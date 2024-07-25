import React, { useState, useEffect } from 'react'
import axios from 'axios'
import SubMenu from '../SubMenu'
import { Link, useNavigate } from 'react-router-dom'
import '../../style/admin.css'


function ProductList() {
    const [productList, setProductList] = useState();
    const [ paging, setPaging ] = useState({});
    const navigate = useNavigate();

    useEffect(
        ()=>{
            axios.get('/api/admin/getProductList/1')
            .then((result)=>{
                setProductList(result.data.productList)
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
        axios.get(`/api/admin/getProductList/${page}`)
            .then((result) => {
                setPaging(result.data.paging);
                let products = [];
                products = [...productList];
                products = [...products, ...result.data.productList];
                setProductList([...products]); // Merge 한 리스트를 boardList 로 복사
            })
            .catch((err) => { console.error(err) })
    }

    return (
        <div className='adminContainer'>
            <SubMenu />
            <div className='btns' style={{ display: 'flex', margin: '5px' }}>
                <input type="text" /><button>검색</button>
                <button style={{ marginLeft: 'auto' }} onClick={() => { navigate('/writeProduct') }}>상품등록</button>
            </div>
            <div className='productTable'>
                <div className='row'>
                    <div className='col'>번호</div>
                    <div className='col'>상품명</div>
                    <div className='col'>원가</div>
                    <div className='col'>판매가</div>
                    <div className='col'>등록일</div>
                    <div className='col'>사용유무</div>
                </div>
                {
                    (productList) ? (
                        productList.map((product, idx) => {
                            return (
                                <div className='row' key={idx}>
                                    <div className='col'>{product.pseq}</div>
                                    <div className='col' style={{cursor:"pointer"}} onClick={()=>{navigate(`/productView/${product.pseq}`)}}>{product.name}</div>
                                    <div className='col'>{new Intl.NumberFormat('ko-KR').format(product.price1)}원</div>
                                    <div className='col'>{new Intl.NumberFormat('ko-KR').format(product.price2)}원</div>
                                    <div className='col'>{product.indate.substring(0, 10)}</div>
                                    <div className='col'>{product.useyn}</div>
                                </div>
                            )
                        })
                    ) : (null)
                }
            </div>
        </div>
    )
}

export default ProductList
