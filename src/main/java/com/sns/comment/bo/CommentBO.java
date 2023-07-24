package com.sns.comment.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.comment.dao.CommentMapper;

@Service
public class CommentBO {

	@Autowired
	private CommentMapper commentMapper;

	public int addComment(int userId, int postId, String content) {
		return commentMapper.insertComment(userId, postId, content);
	}
}