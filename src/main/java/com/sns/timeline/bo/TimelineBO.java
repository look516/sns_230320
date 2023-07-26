package com.sns.timeline.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.comment.bo.CommentBO;
import com.sns.comment.domain.CommentView;
import com.sns.like.bo.LikeBO;
import com.sns.post.bo.PostBO;
import com.sns.post.entity.PostEntity;
import com.sns.timeline.domain.CardView;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;

@Service
public class TimelineBO {
	
	// 브라우저 -> TimelineController -> TimelineBO -> PostBO -> PostRepository -> DB
	// bo가 남의 dao를 부르면 안 된다. 단 남의 bo는 부를 수 있다.
	// BO가 서로를 부를 때 오류 -> 상호(순환)참조오류 -> 높낮이가 있게 설계해야 한다.
	
	@Autowired
	private PostBO postBO;
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private CommentBO commentBO;
	
	@Autowired
	private LikeBO likeBO;
	
	// input: X
	// output: List<CardView>
	// 비로그인 시에도 카드리스트는 뿌려져야 하므로 Integer userId - null 허용
	public List<CardView> generateCardViewList(Integer userId) {
		// 조인문 많아질수록 재활용 어려워짐 - bo에서 로직으로 합침
		
		List<CardView> cardViewList = new ArrayList<>(); // []
		
		// 글 목록 가져온다.
		List<PostEntity> postList = postBO.getPostList();
		
		// 글 목록 반복문 순회
		// postEntity -> cardView => cardViewList에 담는다.
		for (PostEntity post : postList) { // 0 1 2
			// post에 대응되는 하나의 카드를 만든다.
			CardView card = new CardView();
			
			// 글을 세팅한다.
			card.setPost(post);
			
			// 글쓴이를 세팅한다.
			UserEntity user = userBO.getUserEntityById(post.getUserId());
			card.setUser(user);
			
			
			
			
			
			// 댓글들을 세팅한다.
			List<CommentView> commentViewList = commentBO.generateCommentViewList(post.getId());
			card.setCommentList(commentViewList);
			
			
			
			
			// 좋아요 개수
			int likeCount = likeBO.getLikeCountByPostId(post.getId());
			card.setLikeCount(likeCount);
			
			// 좋아요 여부
			// 이 예외처리를 bo에서 할 것인가? 고민
			boolean filledLike = likeBO.filledLike(post.getId(), userId);
			card.setFilledLike(filledLike);
			
			
			
			
			
			
			// ★★★★★★ cardViewList에 담는다.
			cardViewList.add(card);
		}
		return cardViewList;
	}
}
