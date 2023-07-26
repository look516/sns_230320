package com.sns.like.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeMapper {

	// 콜뎁스 단축키 : 함수 클릭 후 ctrl + alt + h
	// (이 함수를 부르고 있는 것 보여줌) => 확인 후 없을 때 주석처리
//	public int selectLikeCountByPostIdUserId(
//			@Param("postId") int postId,
//			@Param("userId") int userId);
//	
//	
//	public int selectLikeCountByPostId(int postId);
	
	
	// by postId userId, by postId => 하나의 쿼리로 합친다.
	public int selectLikeCountByPostIdOrUserId(
			@Param("postId") int postId,
			@Param("userId") Integer userId);
	
	

	public void insertLike(
			@Param("postId") int postId,
			@Param("userId") int userId);

	public void deleteLikeByPostIdUserId(
			@Param("postId") int postId,
			@Param("userId") int userId);
}