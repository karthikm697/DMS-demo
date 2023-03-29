package dmsdemo.Test;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import dmsdemo.PageObjects.ProjectsPage;
import dmsdemo.TestComponents.BaseTest;

public class ProjectsTest extends BaseTest {
	
	
	DataFormatter formatter=new DataFormatter();
	
	@Test(priority = 1,dataProvider = "data")
	public void addProjects(String email,String pass,String projName,String projType,String projRole,String projUser,String foldername) throws Exception
	{
		loginpg.login(email,pass);
		log.info("logged in");
		ProjectsPage projpg=new ProjectsPage(driver);
		projpg.projectMenu();
		projpg.createProjectIcon();
		projpg.createProject(projName,projType);
		projpg.save();
		Assert.assertEquals(projpg.alert(),"Project created successfully.");
		log.info("Project created");
		Assert.assertTrue(projpg.selProject(projName));
		projpg.addProjRole(projRole);
		projpg.save();
		Assert.assertEquals(projpg.alert(),"Role created successfully");
		log.info("Project Role added");
		Assert.assertTrue(projpg.verifyProjectRole(projRole));
		projpg.addprojUsers(projRole);
		projpg.chooseUserDropdown();
		Assert.assertTrue(projpg.chooseUser(projUser));
		projpg.chooseUserDropdown();
		projpg.save();
		Assert.assertEquals(projpg.alert(),"User added successfully");
		log.info("User added to project");
		Assert.assertTrue(projpg.verifyProjectUsers(projUser));
		projpg.createFolder();
		projpg.addFolder(foldername);
		projpg.saveFolder();
		Assert.assertEquals(projpg.alert(),"Folder created successfully.");
		log.info("Project folder created");
		Assert.assertTrue(projpg.selFolder(foldername));
		projpg.permission();
		projpg.selPermissionDesig(projRole);
		projpg.selUser(projUser);
		projpg.save();
		Assert.assertEquals(projpg.alert(),"Permission added successfully");
		log.info("Folder permission added");
	}

	
	@Test(priority = 2,dataProvider = "data")
	public void addDocumentPermission(String email,String pass,String projName,String projType,String projRole,String projUser,String foldername) throws Exception
	{
		loginpg.login(email,pass);
		ProjectsPage projpg=new ProjectsPage(driver);
		projpg.projectMenu();
		Assert.assertTrue(projpg.selProject(projName));
		
		Assert.assertTrue(projpg.createDocument(foldername));//sel the folder
		projpg.createDocumentIcon();
		projpg.fillDocument("doc1","KarthikM");
		projpg.docVersion("v1");
		projpg.uploadDoc("src\\main\\resources\\sample.pdf");
		projpg.save();
		log.info("document added");
		Assert.assertEquals(projpg.alert(),"Documents saved successfully");
		Assert.assertTrue(projpg.verifyDocument("sample"));
		Assert.assertTrue(projpg.documentActions("sample"));
		projpg.permission();
		projpg.selPermissionDesig(projRole);
		projpg.selUser(projUser);
		projpg.save();
		Assert.assertEquals(projpg.alert(),"Permission added successfully");
		
	}

	
	@DataProvider(name="data")
	public Object[][] getData() throws Exception 
	{
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/dmsdemo/Resources/dmsdemodata.xlsx");
		XSSFWorkbook wb=new XSSFWorkbook(fis);
		XSSFSheet sheet= wb.getSheetAt(0);
		int rowCount=sheet.getPhysicalNumberOfRows();
		XSSFRow row=sheet.getRow(1);
		int colCount=row.getLastCellNum();
		Object data[][]=new Object[rowCount-1][colCount];
		for(int i=0;i<(rowCount-1);i++)
		{
			row=sheet.getRow(i+1);
			for(int j=0;j<colCount;j++)
			{
				XSSFCell cell=row.getCell(j);
				data[i][j]=formatter.formatCellValue(cell);
			}
		}
		return data;	
	}		
	
	
	/*	
	
	@Test(priority = 2)
	public void verifyFolderPermission() throws InterruptedException
	{
		loginpg.login("testuser1@yopmail.com", "Ksfe@123");
		ProjectsPage projpg=new ProjectsPage(driver);
		projpg.projectMenu();
		Assert.assertTrue(projpg.selProject("proj111"));
		Assert.assertTrue(projpg.selFolder("projFolder1"));
	}
	
	@Test(priority = 3)
	public void folderShare() throws InterruptedException
	{
		loginpg.login("karthikm@datamatica.uk", "Dm@12345");
		ProjectsPage projpg=new ProjectsPage(driver);
		projpg.projectMenu();
		Assert.assertTrue(projpg.selProject("proj111"));//projectname
		projpg.createFolder();
		projpg.addFolder("sharefolder");
		projpg.saveFolder();
		Assert.assertEquals(projpg.alert(),"Folder created successfully.");
	
		Assert.assertTrue(projpg.selFolder("sharefolder"));// project foldername to share
		projpg.share();
		projpg.selPermissionDesig("QA");
		projpg.selUser("testuser1");
		projpg.shareBtn();
		Assert.assertEquals(projpg.alert(),"Folder shared successfully");
		
	}
	
	@Test(priority = 4)
	public void verifyFolderShare() throws InterruptedException
	{
		loginpg.login("testuser1@yopmail.com", "Ksfe@123");
		ProjectsPage projpg=new ProjectsPage(driver);
		projpg.projectMenu();
		Assert.assertTrue(projpg.selProject("proj111"));
		Assert.assertTrue(projpg.selFolder("sharefolder"));
	}


	@Test(priority = 6)
	public void verifyDocumentPermission() throws InterruptedException
	{
		loginpg.login("testuser1@yopmail.com", "Ksfe@123");
		ProjectsPage projpg=new ProjectsPage(driver);
		projpg.projectMenu();
		Assert.assertTrue(projpg.selProject("proj111"));
		Assert.assertTrue(projpg.createDocument("projFolder1"));//foldername
		Assert.assertTrue(projpg.verifyDocument("testdatas"));
	}
	@Test (priority= 7)
	public void documentShare() throws Exception
	{
		loginpg.login("karthikm@datamatica.uk", "Dm@12345");
		ProjectsPage projpg=new ProjectsPage(driver);
		projpg.projectMenu();
		Assert.assertTrue(projpg.selProject("proj111"));
		Assert.assertTrue(projpg.createDocument("sharefolder"));
		projpg.createDocumentIcon();
		projpg.fillDocument("doc1","KarthikM");
		projpg.docVersion("v1");
		projpg.uploadDoc("C:\\Users\\DM Administrator\\Documents\\testdatas.xls");
		projpg.save();
		Assert.assertEquals(projpg.alert(),"Documents saved successfully");
		Assert.assertTrue(projpg.verifyDocument("testdatas"));
		Assert.assertTrue(projpg.documentActions("testdatas"));
		projpg.share();
		projpg.selPermissionDesig("QA");
		projpg.selUser("testuser1");
		projpg.save();
		Assert.assertEquals(projpg.alert(),"Document shared successfully");
	}
	@Test(priority= 8)
	public void verifyDocumentShare() throws InterruptedException
	{
		loginpg.login("testuser1@yopmail.com", "Ksfe@123");
		ProjectsPage projpg=new ProjectsPage(driver);
		projpg.projectMenu();
		Assert.assertTrue(projpg.selProject("proj111"));
		Assert.assertTrue(projpg.selFolder("sharefolder"));
		Assert.assertTrue(projpg.verifyDocument("testdatas"));
	}
	
*/	
	
}
