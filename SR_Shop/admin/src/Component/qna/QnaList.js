import React, { useEffect, useState } from 'react'
import SubMenu from '../SubMenu'
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function QnaList() {
    const [qnaList, setQnaList] = useState();
    const [paging, setPaging]=useState({});
    const navigate = useNavigate();

    useEffect(
        ()=>{
            axios.get('/api/admin/getQnaList/1')
            .then((result)=>{ 
                setQnaList(result.data.qnaList) 
                setPaging( result.data.paging )
            })
            .then((err)=>{console.error(err)})
        },[]
    )

    useEffect(
        () => {
            window.addEventListener("scroll", handleScroll);
            return () => {
                window.removeEventListener("scroll", handleScroll);
            }
        }
    );

    const handleScroll = () => {
        const scrollHeight = document.documentElement.scrollHeight - 30; // 스크롤이 가능한 크기
        const scrollTop = document.documentElement.scrollTop;  // 현재 위치
        const clientHeight = document.documentElement.clientHeight; // 내용물의 크기
        if (paging.page && (scrollTop + clientHeight >= scrollHeight)) {
            onPageMove(Number(paging.page) + 1);
        }
    }

    function onPageMove(page) {
        axios.get(`/api/admin/getQnaList/${page}`)
            .then((result) => {
                setPaging(result.data.paging);
                let qnas = [];
                qnas = [...qnaList];
                qnas = [...qnas, ...result.data.qnaList];
                setQnaList([...qnas]);
            })
            .catch((err) => { console.error(err) })
    }

    

    return (
        <div className='adminContainer'>
            <SubMenu />
            <div className='btns' style={{display:"flex", margin:"5px"}}>
                <input type="text" /><button>검색</button>
                <button style={{marginLeft:"auto"}} onClick={()=>{ navigate('/writeproduct') }}>상품등록</button>
            </div>
            <div className='productTable'>
                <div className='row'>
                    <div className='col'>번호</div>
                    <div className='col'style={{flex:"3"}}>제목</div>
                    <div className='col'>작성자</div>
                    <div className='col'>답변여부</div>
                    <div className='col'>작성일</div>
                </div>
                {
                    (qnaList)?(
                        qnaList.map((qna, idx)=>{
                            return (
                                <div className='row'>
                                    <div className='col'>{qna.qseq}</div>
                                    <div className='col' style={{flex:"3"}} onClick={()=>{navigate(`/qnaView/${qna.qseq}`)}}>{qna.subject}</div>
                                    <div className='col'>{qna.userid}</div>
                                    <div className='col'>{(qna.reply)?(<div>Y</div>):(<div>N</div>)}</div>
                                    <div className='col'>{qna.indate.substring(2, 10)}</div>
                                </div>
                            )
                        })
                    ):(<span>loading...</span>)
                }
            </div>
        
        </div>
    )
}

export default QnaList
