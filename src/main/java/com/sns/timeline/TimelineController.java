package com.sns.timeline;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sns.timeline.bo.TimelineBO;
import com.sns.timeline.domain.CardView;

@RequestMapping("/timeline")
@Controller
public class TimelineController {
	
	
//	@Autowired
//	private PostBO postBO;
//	
//	@Autowired
//	private CommentBO commentBO;
	
	@Autowired
	private TimelineBO timelineBO;
	
	@GetMapping("/timeline_view")
	public String timelineView(Model model, HttpSession session) {
		// postList jpa
//		List<PostEntity> postList = postBO.getPostList();
//		List<Comment> commentList = commentBO.getCommentList();
		
		// commentList => model
		
//		model.addAttribute("postList", postList);
//		model.addAttribute("commentList", commentList);
		
		
		Integer userId = (Integer)session.getAttribute("userId");
		
		
		
		// 디버깅 걸어서 확인해보기
		List<CardView> cardList = timelineBO.generateCardViewList(userId);
		
		model.addAttribute("cardList", cardList);

		
		model.addAttribute("view", "timeline/timeline");
		return "template/layout";
	}
}
