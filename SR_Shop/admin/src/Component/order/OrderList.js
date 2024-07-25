import React, { useState, useEffect } from 'react'
import SubMenu from '../SubMenu'
import axios from 'axios'
import { Link, useNavigate } from 'react-router-dom'
import '../../style/admin.css'


function OrderList() {
    
    const [orderList, setOrderList] = useState();
    const [ paging, setPaging ] = useState({});
    const navigate = useNavigate();


    useEffect(
        () => {
            axios.get('/api/admin/getOrderList/1')
            .then((result)=>{
                setOrderList(result.data.orderList)
                setPaging(result.data.paging);
            })
            .catch((err)=>{console.error(err)})
        }, []
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
        axios.get(`/api/admin/getOrderList/${page}`)
            .then((result) => {
                setPaging(result.data.paging);
                let orders = [];
                orders = [...orderList];
                orders = [...orders, ...result.data.orderList];
                setOrderList([...orders]);
            })
            .catch((err) => { console.error(err) })
    }



    function onResult(result){
        if( result=='1') {return "주문완료"}
        if( result=='2') {return "배송중"}
        if( result=='3') {return "배송완료"}
        if( result=='4') {return "구매확정"}
    }

    function onStyle( result ){
        if(result == '1'){ return { color : "black"}}
        if(result == '2'){ return { color : "black", fontWeight:"bold"}}
        if(result == '3'){ return { color : "blue", fontWeight:"bold" }}
        if(result == '4'){ return { color : "red", fontWeight:"bold"}}
    }

    async function nextProcess(){

        try{
            for( let i = 0; i < odseqList.length; i++){
                const result = await axios.post('/api/admin/nextResult', null, {params:{odseq:odseqList[i]} } )
            }
            const result = await axios.get(`/api/admin/getOrderList/1`)
            setOrderList(result.data.orderList);
            setPaging(result.data.paging);
            for( let i = 0; i < orderList.length; i++){
                document.getElementById('id'+odseqList[i]).checked = false;
            }
            odseqList = [];

        }catch(err){console.error(err);}

    }

    let odseqList =[];

    function setOdseqList(odseq, ch){
        if(ch == true){
            odseqList.push( odseq);
        }else{
            odseqList = odseqList.filter( (value, idx, arr)=>{return value != odseq})
        }
    }

    return (
        <div className='adminContainer'>
            <SubMenu />
            <div className='btns' style={{display:"flex", margin:"5px"}}>
                <input type="text" /><button>검색</button>
                <button style={{marginLeft:"auto"}} onClick={()=>{ nextProcess() }}>다음단계로</button>
            </div>
            <div className='productTable'>
                <div className='row'>
                    <div className='col'>선택</div>
                    <div className='col' style={{flex:'1'}}>주문번호(처리상태)</div>
                    <div className='col'>상품명</div><div className='col'>주문자</div><div className='col' style={{flex:'2'}} >배송주소</div><div className='col'>가격</div><div className='col'>주문일</div>
                </div>
                {
                    (orderList)?(
                        orderList.map((order, idx)=>{
                            return (
                                <div className='row'>
                                    <div className='col'>
                                        <input type="checkbox" value={order.odseq} id={'id' + order.odseq} onChange={(e)=>{setOdseqList( order.odseq, e.currentTarget.checked )}} />
                                    </div>
                                    <div className='col' style={{flex:'1'}}>{order.oseq}-
                                        {order.odseq}<br/>
                                        <span style={onStyle(order.result)}>({ onResult(order.result) })</span> 
                                    </div>
                                    <div className='col'>{order.pname}</div>
                                    <div className='col'>{order.userid}<br />({order.mname})</div>
                                    <div className='col' style={{flex:'2'}}>{order.address1} {order.address2}</div>
                                    <div className='col'>{new Intl.NumberFormat('ko-KR').format(order.price2)}원</div>
                                    <div className='col'>{order.indate.substring(0, 10)}</div>
                                </div>
                            )
                        })
                    ):(<span>loading...</span>)
                }
            </div>
        
        </div>
    )
}

export default OrderList
