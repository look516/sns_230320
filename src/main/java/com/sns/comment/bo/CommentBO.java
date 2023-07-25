package com.sns.comment.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.comment.dao.CommentMapper;
import com.sns.comment.domain.Comment;
import com.sns.comment.domain.CommentView;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;

@Service
public class CommentBO {

	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private UserBO userBO;
	
	
	
	
	public int addComment(int userId, int postId, String content) {
		return commentMapper.insertComment(userId, postId, content);
	}
	
	
	
	
	
	// input: postId
	// output: 가공된 댓글 리스트
	public List<CommentView> generateCommentViewList(int postId) {
		// 결과 리스트
		List<CommentView> commentViewList = new ArrayList<>();
		
		// 글에 해당하는 댓글들
		List<Comment> commentList = commentMapper.selectCommentListByPostId(postId);
		
		// 반복문 순회 comment -> commentView -> commentViewList에 담는다.
		for (Comment comment : commentList) {
			CommentView commentView = new CommentView();
			commentView.setComment(comment);
			
			UserEntity user = userBO.getUserEntityById(comment.getUserId());
			commentView.setUser(user); // 댓글쓴이
			
			commentViewList.add(commentView);
		}
				
		return commentViewList;
	}
}