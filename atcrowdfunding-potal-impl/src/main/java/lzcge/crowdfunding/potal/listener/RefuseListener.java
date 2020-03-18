package lzcge.crowdfunding.potal.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * @description:
 * @author: lzcge
 * @create: 2020-03-18
 **/
public class RefuseListener implements ExecutionListener {

	@Override
	public void notify(DelegateExecution delegateExecution) throws Exception {
		System.out.println("refuseListener");
	}
}
