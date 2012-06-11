package kf.test.flow;

import kf.web.component.page.support.BccAdminConsole;
import kf.web.component.page.support.BccMergeAssetsPage;
import kf.web.component.page.support.BccProjectDetailsPage;
import kf.web.component.page.support.BccProjectsPage;
import kf.web.component.panel.bcc.ProjectAssetsTab;
import kf.web.component.panel.bcc.ProjectTasksTab;
import kf.web.component.panel.bcc.ProjectAssetsTab.AssetItem;
import kf.web.component.panel.bcc.ProjectTasksTab.Tasks;

public class CommonBccFlow {

    // "ProductImportProcess", "Production"
    public static void deployAllImportProjects(final String prefix, final String siteName, final String waitTimeMils) {
        // BccLoginPage bccLoginPage = new BccLoginPage();
        // bccLoginPage.login("admin", "admin");
        BccProjectsPage bccProjectsPage = new BccProjectsPage();
        bccProjectsPage.filterSelectProjectStatus("Active Project");
        bccProjectsPage.filterEnterNameContains(prefix);
        bccProjectsPage.filterApplyFilterOptions();

        bccProjectsPage.sortProjectsByDate();

        bccProjectsPage.printProjects();

        while(bccProjectsPage.hasProject(prefix, true)) {
            BccProjectDetailsPage bccProjectDetailsPage = bccProjectsPage.viewProjectDetails(prefix, true);
            ProjectTasksTab projectTasksTab = bccProjectDetailsPage.tabHolder.switchToTasksTab();
            projectTasksTab.approveImport();
            BccAdminConsole bccAdminConsole = new BccAdminConsole();

            bccAdminConsole.waitForDeploying(siteName, waitTimeMils);
            bccProjectsPage = new BccProjectsPage();
            bccProjectsPage.refreshProjectsList();
        }
    }

    public static void deleteAllImportProjects(final String prefix) {
        BccProjectsPage bccProjectsPage = new BccProjectsPage();
        bccProjectsPage.filterSelectProjectStatus("Active Project");
        bccProjectsPage.filterEnterNameContains(prefix);
        bccProjectsPage.filterApplyFilterOptions();

        bccProjectsPage.sortProjectsDescBy("Date");

        while(bccProjectsPage.hasProject(prefix, true)) {
            BccProjectDetailsPage bccProjectDetailsPage = bccProjectsPage.viewProjectDetails(prefix, true);
            ProjectTasksTab projectTasksTab = bccProjectDetailsPage.tabHolder.switchToTasksTab();
            if(projectTasksTab.isActionSelectable(Tasks.RETRY_DEPLOY)) {
                projectTasksTab.returnToImport();
            }
            projectTasksTab.deleteProject();
            bccProjectsPage.refreshProjectsList();
        }
    }

    public static void mergeConflictedAssets(final String prefix, final String siteName, final int waitTimeMils) {
        BccProjectsPage bccProjectsPage = new BccProjectsPage();
        bccProjectsPage.filterSelectProjectStatus("Active Project");
        bccProjectsPage.filterEnterNameContains(prefix);
        bccProjectsPage.filterApplyFilterOptions();

        bccProjectsPage.sortProjectsDescBy("Date");

        while(bccProjectsPage.hasProject(prefix, true)) {
            BccProjectDetailsPage bccProjectDetailsPage = bccProjectsPage.viewProjectDetails(prefix, true);
            ProjectAssetsTab projectAssetsTab = bccProjectDetailsPage.tabHolder.switchToAssetsTab();
            // optional
            projectAssetsTab.waitForAssets(20000);
            AssetItem assetItem;
            do {               
                projectAssetsTab.collectAssets();
                assetItem = projectAssetsTab.getFirstUnmergedAsset();
                if(assetItem != null) {
                    BccMergeAssetsPage mergeAssetsPage = assetItem.mergeAction();
                    mergeAssetsPage.selectAllLatestProperties();
                    projectAssetsTab = mergeAssetsPage.mergeSelectedPrperties();
                }
            }
            while(assetItem != null);

            ProjectTasksTab projectTasksTab = bccProjectDetailsPage.tabHolder.switchToTasksTab();
            projectTasksTab.approveImport();

            BccAdminConsole bccAdminConsole = new BccAdminConsole();
            bccAdminConsole.waitForDeploying(siteName, waitTimeMils);

            bccProjectsPage = new BccProjectsPage();
            bccProjectsPage.refreshProjectsList();
        }
    }
}
