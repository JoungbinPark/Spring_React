package com.himedia.srshopserver.service;

import com.himedia.srshopserver.dao.CustomerDao;
import com.himedia.srshopserver.dto.Paging;
import com.himedia.srshopserver.entity.Qna;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CustomerService {

    @Autowired
    CustomerDao cdao;

    public List<Qna> getQnalist( Paging paging ) {
        return cdao.getQnalist(paging);
    }

    public void writeQna(Qna qna) {
        cdao.writeQna( qna);
    }

    public Qna getQna(int qseq) {
        return cdao.getQna( qseq );
    }
}
