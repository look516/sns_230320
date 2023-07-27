package com.sns.post.bo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sns.comment.dao.CommentMapper;
import com.sns.comment.domain.Comment;
import com.sns.common.FileManagerService;
import com.sns.like.dao.LikeMapper;
import com.sns.post.dao.PostMapper;
import com.sns.post.dao.PostRepository;
import com.sns.post.domain.Post;
import com.sns.post.entity.PostEntity;

@Service
public class PostBO {
	
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private PostMapper postMapper;  // mybatis (삭제 이후부터 사용)
	
	@Autowired
	private PostRepository postRepository; // JPA 현재 사용
	
	@Autowired
	private FileManagerService fileManager;
	
	
	
	
	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private LikeMapper likeMapper;
	
	
	
	
	public List<PostEntity> getPostList() {
		return postRepository.findAllByOrderByIdDesc();
	}
	
	
	
	
	
	public PostEntity addPost(int userId, String userLoginId, String content, MultipartFile file) {
		String imagePath = null;
		
		// 이미지가 있으면 업로드 후 imagePath를 받아옴
		if (file != null) {
			imagePath = fileManager.saveFile(userLoginId, file);
		}
		
		return postRepository.save(
				PostEntity.builder()
				.userId(userId)
				.content(content)
				.imagePath(imagePath)
				.build());
	}
	
	
	public void deletePostByPostIdAndUserId(int postId, int userId) {
		
		// 기존 글을 가져온다.
		Post post = postMapper.selectPostByPostIdAndUserId(postId, userId);
		if (post == null) {
			logger.error("###[글 삭제] post is null. postId:{}, userId:{}", postId, userId);
			return;
		}
		
		// 기존 이미지가 있으면 삭제
		if (post.getImagePath() != null) {
			fileManager.deleteFile(post.getImagePath());
		}
		
		// 기존 댓글을 가져온다. - 기존 댓글이 있으면 삭제
		List<Comment> comment = commentMapper.selectCommentListByPostId(postId);
		if (comment != null) {
			commentMapper.deleteCommentByPostId(postId);
		}
		// 진짜 댓글이 없는 건지, 오류로 인해 댓글을 가져올 수 없는 건지 확인 어떻게 가능?
		
		
		// 기존 좋아요를 가져온다. - 기존 좋아요가 있으면 삭제
		int like = likeMapper.selectLikeCountByPostIdOrUserId(postId, null);
		if (like > 0) {
			likeMapper.deleteLikeByPostIdOrUserId(postId, null);
		}
		
		// 기존 글이 있으면 삭제
		postMapper.deletePostByPostIdAndUserId(postId, userId);
	}
	
	
	
}
