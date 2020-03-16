import org.activiti.engine.*;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @description:
 * @author: lzcge
 * @create: 2020-03-10
 **/
public class Activity_Test {
	ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/spring-*.xml");
	ProcessEngine processEngine = (ProcessEngine) ioc.getBean("processEngine");


	//1.创建流程引擎,创建23张表
	@Test
	public void test01(){

		System.out.println("processEngine="+processEngine);

	}

	/**
	 * 部署流程定义
	 */
	@Test
	public void test02(){
		System.out.println("processEngine="+processEngine);
		RepositoryService repositoryService = processEngine.getRepositoryService();
		Deployment deployment =  repositoryService.createDeployment().addClasspathResource("bpmn/ac_test.bpmn").deploy();
		System.out.println("deployment="+deployment);

	}


	/**
	 * 查询部署流程定义
	 */
	@Test
	public void test03(){
		RepositoryService repositoryService = processEngine.getRepositoryService();
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		List<ProcessDefinition> list = processDefinitionQuery.list();
		for (ProcessDefinition pro: list ) {
			System.out.println(pro.getId());
			System.out.println(pro.getKey());
			System.out.println(pro.getName());
			System.out.println(pro.getVersion());

		}

	}

	//4.启动流程实例
	/**
	 * act_hi_actinst, 历史的活动的任务表.
	 * act_hi_procinst, 历史的流程实例表.
	 * act_hi_taskinst, 历史的流程任务表
	 * act_ru_execution, 正在运行的任务表.
	 * act_ru_task, 运行的任务数据表.
	 */
	@Test
	public void test04(){
		ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();

		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
		System.out.println("processInstance="+processInstance);
	}


	//获取流程任务，完成任务
	@Test
	public void testTask(){
		ProcessEngine processEngine = (ProcessEngine) ioc.getBean("processEngine");
		RepositoryService repositoryService = processEngine.getRepositoryService();

		//查询流程定义
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionKey("myProcess")
				.latestVersion()
				.singleResult();

		TaskService taskService = processEngine.getTaskService();

		TaskQuery taskQuery = taskService.createTaskQuery();
		List<Task> list1 = taskQuery.taskAssignee("zhangsan").list(); //获取zhangsan的任务列表

		for (Task task : list1) {
			System.out.println("zhangsan的任务="+task.getName());
		}

	}

	//完成任务
	@Test
	public void testTask2(){
		ProcessEngine processEngine = (ProcessEngine) ioc.getBean("processEngine");
		RepositoryService repositoryService = processEngine.getRepositoryService();

		//查询流程定义
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionKey("myProcess")
				.latestVersion()
				.singleResult();

		TaskService taskService = processEngine.getTaskService();

		TaskQuery taskQuery = taskService.createTaskQuery();
		List<Task> list1 = taskQuery.taskAssignee("zhangsan").list(); //获取zhangsan的任务列表
//		List<Task> list1 = taskQuery.taskId("204").list();//获取zhangsan的任务列表
		List<Task> list2 = taskQuery.taskAssignee("lisi").list();//获取lisi的任务列表

		System.out.println("完成任务前查询列表：");
		for (Task task : list1) {
			System.out.println("zhangsan的任务="+task.getName());
			taskService.complete(task.getId());
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		for (Task task : list2) {
			System.out.println("lisi的任务="+task.getName());
		}

		System.out.println("============================================");

		list1 = taskQuery.taskAssignee("zhangsan").list(); //获取zhangsan的任务列表
		list2 = taskQuery.taskAssignee("lisi").list();//获取lisi的任务列表
		System.out.println("完成任务后查询列表：");
		for (Task task : list1) {
			System.out.println("zhangsan的任务="+task.getName());
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		for (Task task : list2) {
			System.out.println("lisi的任务="+task.getName());
			taskService.complete(task.getId());
		}
	}

	/**
	 *历史数据查询
	 */

	@Test
	public void test06(){
		ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();

		HistoryService historyService = processEngine.getHistoryService();

		HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();

		//根据实例的ID进行查询
		HistoricProcessInstance historicProcessInstance = historicProcessInstanceQuery.processInstanceId("101").finished().singleResult();

		System.out.println("historicProcessInstance="+historicProcessInstance);
	}





}
