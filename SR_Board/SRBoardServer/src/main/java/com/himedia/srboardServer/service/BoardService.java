package com.himedia.srboardServer.service;

import com.himedia.srboardServer.dao.BoardDao;
import com.himedia.srboardServer.dto.Paging;
import com.himedia.srboardServer.entity.Board;
import com.himedia.srboardServer.entity.Reply;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BoardService {

    @Autowired
    BoardDao bdao;

    public List<Board> getBoardList(Paging paging) {
        return bdao.getBoardList(paging);
    }

    public int getAllCount() {
        return bdao.getAllCount();
    }

    public Board getBoard(int num) {
        return bdao.getBoard(num);
    }

    public List<Reply> getReplyList(int num) {
        return bdao.getReplyList(num);
    }

    public void insertReply(Reply reply) {
        bdao.insertReply(reply);
    }

    public void deleteReply(int replynum) {
        bdao.deleteReply( replynum);
    }

    public void updateReadCount(int num) {
        bdao.updateReadCount( num );
    }

    public void insertBoard(Board board) {
        bdao.insertBoard(board);
    }

    public void updateBoard(Board board) {
        bdao.updateBoard(board);
    }

    public void deleteBoard(int num) {
        bdao.delteBoard(num);
    }
}
