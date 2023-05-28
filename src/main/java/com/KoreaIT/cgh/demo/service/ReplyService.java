package com.KoreaIT.cgh.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.cgh.demo.repository.ReplyRepository;
import com.KoreaIT.cgh.demo.util.Ut;
import com.KoreaIT.cgh.demo.vo.Reply;
import com.KoreaIT.cgh.demo.vo.ResultData;

@Service
public class ReplyService {
	
    /*
	 * 필드선언부
	 * 
	 * @Autowired: 스프링에 의해 자동으로 의존성 주입을 받습니다.  
	 * Rq rq: Rq 객체를 주입받습니다.
	 */
	
	
	@Autowired
	private ReplyRepository replyRepository;
	public ReplyService(ReplyRepository replyRepository) {
		this.replyRepository = replyRepository;
	}

	public ResultData<Integer> writeReply(int actorId, String relTypeCode, int relId, String body) {
		replyRepository.writeReply(actorId, relTypeCode, relId, body);

		int id = replyRepository.getLastInsertId();

		return ResultData.from("S-1", Ut.f("%d번 댓글이 생성되었습니다", id), "id", id);
	}
	
	
	public void deleteReply(int relId) {
		replyRepository.deleteReply(relId);
	}
	public ResultData modifyReply(int id, String body) {
		replyRepository.modifyReply(id,  body);
		Reply reply = getReply(id);
		return ResultData.from("S-1", Ut.f("%d번 댓글을 수정 했습니다", id), "reply", reply);
	}
	public List<Reply> getForPrintReplies(int actorId, String relTypeCode, int relId) {
		List<Reply> replies = replyRepository.getForPrintReplies(actorId, relTypeCode, relId);

		for (Reply reply : replies) {
			controlForPrintData(actorId, reply);
		}

		return replies;
	}
	public Reply getReply(int id) {
		return replyRepository.getReply(id);
	}
	
	
	public Reply getForPrintReplise(int loginedMemberId, int id) {
		return replyRepository.getForPrintReplise(loginedMemberId,id);
	}
	


	// 댓글 권한
	
	
	
	private void controlForPrintData(int actorId, Reply reply) {
		if (reply == null) {
			return;
		}

		ResultData actorCanDeleteRd = actorCanDelete(actorId, reply);
		reply.setActorCanDelete(actorCanDeleteRd.isSuccess());

		ResultData actorCanModifyRd = actorCanModify(actorId, reply);
		reply.setActorCanModify(actorCanModifyRd.isSuccess());
	}

	public ResultData actorCanModify(int loginedMemberId, Reply reply) {
		if (reply.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", Ut.f("해당 댓글에 대한 권한이 없습니다"));
		}
		return ResultData.from("S-1", "수정 가능");
	}

	private ResultData actorCanDelete(int actorId, Reply reply) {
		if (reply == null) {
			return ResultData.from("F-1", "댓글이 존재하지 않습니다");
		}

		if (reply.getMemberId() != actorId) {
			return ResultData.from("F-2", "해당 댓글에 대한 권한이 없습니다");
		}

		return ResultData.from("S-1", "삭제 가능");
	}


	
	
	//좋아요

	public ResultData increaseGoodReationPoint(int relId) {
		int affectedRow = replyRepository.increaseGoodReationPoint(relId);
		if (affectedRow == 0) {
			return ResultData.from("F-1", "댓글은 없습니다", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "좋아요 증가", "affectedRow", affectedRow);
	}
	public ResultData increaseBadReationPoint(int relId) {
		int affectedRow = replyRepository.increaseBadReationPoint(relId);
		if (affectedRow == 0) {
			return ResultData.from("F-1", "댓글은 없습니다", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "싫어요 증가", "affectedRow", affectedRow);
	}

	public ResultData decreaseGoodReationPoint(int relId) {
		int affectedRow = replyRepository.decreaseGoodReationPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "댓글은 없습니다", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "좋아요 감소", "affectedRow", affectedRow);
	}

	public ResultData decreaseBadReationPoint(int relId) {
		int affectedRow = replyRepository.decreaseBadReationPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "댓글은 없습니다", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "싫어요 감소", "affectedRow", affectedRow);

	}

}