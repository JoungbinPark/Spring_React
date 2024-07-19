package com.himedia.srboardServer.dao;

import com.himedia.srboardServer.dto.Paging;
import com.himedia.srboardServer.entity.Board;
import com.himedia.srboardServer.entity.Reply;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardDao {

    @Autowired
    private EntityManager em;

    public List<Board> getBoardList(Paging paging) {
        String sql = "select b from Board b order by b.num desc";
        List<Board> boardList
                = em.createQuery(sql, Board.class)
                .setFirstResult( paging.getStartNum()-1)
                .setMaxResults( paging.getDisplayRow())
                .getResultList();
        return boardList;
    }

    public int getAllCount() {
        String sql = "select count(b) from Board b";
        long count = (long)em.createQuery(sql).getSingleResult();
        System.out.println(count);
        return (int) count;
    }

    public Board getBoard(int num) {
        Board board = em.find(Board.class, num);
        return board;
    }

    public List<Reply> getReplyList(int num) {
        String sql = "select r from Reply r where r.boardnum=:num order by r.replynum desc";
        List<Reply> replyList
                = em.createQuery(sql, Reply.class)
                .setParameter("num", num)
                .getResultList();
        return replyList;
    }

    public void insertReply(Reply reply) {
        em.persist(reply);
    }

    public void deleteReply(int replynum) {
        Reply reply = em.find(Reply.class, replynum);
        em.remove(reply);
    }

    public void updateReadCount(int num) {
        Board board = em.find(Board.class, num);
        board.setReadcount(board.getReadcount()+1);
    }

    public void insertBoard(Board board) {
        em.persist(board);
    }

    public void updateBoard(Board board) {
        Board upBoard = em.find(Board.class, board.getNum());
        upBoard.setTitle(board.getTitle());
        upBoard.setContent(board.getContent());
        upBoard.setImage(board.getImage());
        upBoard.setSavefilename(board.getSavefilename());
    }


    public void delteBoard(int num) {
        Board board = em.find(Board.class, num);
        em.remove(board);
    }
}
