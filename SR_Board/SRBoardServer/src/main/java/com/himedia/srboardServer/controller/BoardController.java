package com.himedia.srboardServer.controller;

import com.himedia.srboardServer.dto.Paging;
import com.himedia.srboardServer.entity.Board;
import com.himedia.srboardServer.entity.Reply;
import com.himedia.srboardServer.service.BoardService;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

@RestController
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardService bs;

    @GetMapping("/getBoardList/{page}")
    public HashMap<String, Object> getBoardList(@PathVariable("page") int page){
        HashMap<String, Object> result = new HashMap<>();
        Paging paging = new Paging();
        paging.setPage( page );
        int count = bs.getAllCount();
        paging.setTotalCount( count );
        paging.calPaging();
        result.put("boardList", bs.getBoardList(paging));
        result.put("paging", paging);

        return result;
    }

    @GetMapping("/getBoard/{num}")
    public HashMap<String, Object> getBoard(@PathVariable("num") int num){
        HashMap<String, Object> result = new HashMap<>();
        //bs.updateReadCount( num );
        result.put("board", bs.getBoard(num));
        return result;
    }

    @GetMapping("/getReplyList/{num}")
    public HashMap<String, Object> getReplyList(@PathVariable("num") int num){
        HashMap<String, Object> result = new HashMap<>();
        result.put("replyList", bs.getReplyList(num));
        return result;
    }

    @PostMapping("/addReply")
    public HashMap<String, Object> addReply(@RequestBody Reply reply){
        HashMap<String, Object> result = new HashMap<>();
        bs.insertReply( reply);
        result.put("msg", "ok");
        return result;
    }

    @GetMapping("/deleteReply/{replynum}")
    public HashMap<String, Object> deleteReply(@PathVariable("replynum") int replynum){
        HashMap<String, Object> result = new HashMap<>();
        bs.deleteReply(replynum);
        result.put("msg", "ok");
        return result;
    }

    @Autowired
    ServletContext context;

    @PostMapping("/fileupload")
    public HashMap<String, Object> fileUpload(@RequestParam("image")MultipartFile file){
        HashMap<String, Object> result = new HashMap<>();
        String path = context.getRealPath("/images");
        Calendar today = Calendar.getInstance();
        long dt = today.getTimeInMillis();

        String filename = file.getOriginalFilename();
        String fn1 = filename.substring(0, filename.indexOf("."));  //왼쪽파일이름
        String fn2 = filename.substring(filename.indexOf(".")); //오른쪽 확장자
        String uploadPath = path+"/" + fn1+ dt+ fn2;
        try{
            file.transferTo(new File(uploadPath));
            result.put("image", filename);
            result.put("savefilename", fn1+dt+fn2);
        }catch(IllegalStateException | IOException e){
            e.printStackTrace();
        }
        return result;
    }

    @PostMapping("/insertBoard")
    public HashMap<String, Object> insertBoard(@RequestBody Board board){
        bs.insertBoard( board );
        HashMap<String, Object> result = new HashMap<>();
        result.put("msg", "ok");
        return result;
    }

    @GetMapping("/updateReadcount/{num}")
    public HashMap<String, Object> updateReadCount( @PathVariable("num") int num){
        HashMap<String, Object> result = new HashMap<>();
        bs.updateReadCount( num );
        result.put("msg", "ok");
        return result;
    }

    @PostMapping("/updateBoard")
    public HashMap<String, Object> updateBoard(@RequestBody Board board){
        HashMap<String, Object> result = new HashMap<>();
        bs.updateBoard( board );
        result.put("msg", "ok");
        return result;
    }

    @GetMapping("/deleteBoard/{num}")
    public HashMap<String, Object> deleteBoard(@PathVariable("num") int num){
        HashMap<String, Object> result = new HashMap<>();
        bs.deleteBoard(num);
        result.put("msg", "ok");
        return result;
    }

}
