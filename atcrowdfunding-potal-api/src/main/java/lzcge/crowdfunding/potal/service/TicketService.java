package lzcge.crowdfunding.potal.service;


import lzcge.crowdfunding.entity.Member;
import lzcge.crowdfunding.entity.Ticket;

public interface TicketService {

	Ticket getTicketByMemberId(Integer id);

	void saveTicket(Ticket  ticket);

	void updatePstep(Ticket ticket);

	void updatePiidAndPstep(Ticket ticket);

	Member getMemberByPiid(String processInstanceId);

	void updateStatus(Member member);

}
