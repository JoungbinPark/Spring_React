import React, { useEffect, useState } from 'react'
import SubMenu from '../SubMenu'
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';

import '../../style/admin.css'

function QnaView() {
    const [qna, setQna] = useState({});
    const [reply, setReply] = useState({});
    const navigate = useNavigate();
    const {qseq} = useParams();

    useEffect(
        ()=>{
            axios.get('/api/customer/getQna/' + qseq)
            .then((result)=>{
                setQna(result.data.qna);
            })
        },[]
    )

    async function writeReply(){
        try{
            await axios.post('/api/admin/writeReply', null, {params:{reply, qseq}})
            const result = await axios.get('/api/customer/getQna/' + qseq)
            setQna(result.data.qna);

        }catch(err){console.error(err)}        
    }

    return (
        <div className='adminContainer'>
            <SubMenu />
            <h2>Qna View</h2>
            <div className='productTable'>
                <div className="field">
                    <label>번호</label><div>{qna.qseq}</div>
                </div>
                <div className="field">
                    <label>제목</label><div>{qna.subject}</div>
                </div>
                <div className="field">
                    <label>작성자</label><div>{qna.userid}</div>
                </div>
                <div className="field">
                        <label>내용</label><div>{qna.content}</div>
                </div>

                <div className="field">
                    <label>작성일시</label><div>{(qna.indate+"").substring(0,10)}</div>
                </div>
                <div className="field">
                    <label>답변</label>
                    {(qna.reply)?( <div>{qna.reply}</div>):(
                        <div>
                        <input type="text" size='80' value={qna.reply} onChange={(e)=>{setReply(e.currentTarget.value)}} />
                        <button onClick={()=>{writeReply()}}>답변 입력</button>
                        </div>
                        )}
                </div>
                <div className="btns">
                    <button onClick={()=>{navigate('/qnaList')}}>목록으로</button>

                </div>
            </div>
        </div>
    )
}

export default QnaView
