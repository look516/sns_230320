package com.sns.timeline.domain;

import java.util.List;

import com.sns.comment.domain.CommentView;
import com.sns.post.entity.PostEntity;
import com.sns.user.entity.UserEntity;

import lombok.Data;

// View 용 객체
// -- 글 1개와 매핑됨
@Data // getters/setters
public class CardView {
	// 글 1개
	private PostEntity post;
	
	// 글쓴이 정보
	// password 등 중요한 정보 때문에 따로 객체를 구성하기도 함
	private UserEntity user;
	
	// 댓글들
	private List<CommentView> commentList;
	
	// 좋아요들 개수
	private int likeCount;
	
	// 내가 좋아요를 눌렀는지 여부
	private boolean filledLike;
}
