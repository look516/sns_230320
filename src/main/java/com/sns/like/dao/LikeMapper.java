package com.sns.like.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeMapper {

	public int selectLikeCountByPostIdUserId(
			@Param("postId") int postId,
			@Param("userId") int userId);

	public void insertLike(
			@Param("postId") int postId,
			@Param("userId") int userId);

	public void deleteLikeByPostIdUserId(
			@Param("postId") int postId,
			@Param("userId") int userId);
}