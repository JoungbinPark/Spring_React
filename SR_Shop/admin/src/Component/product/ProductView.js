import React, { useState, useEffect } from 'react'
import axios from 'axios'
import SubMenu from '../SubMenu'
import { useNavigate, useParams } from 'react-router-dom'
import '../../style/admin.css'


function ProductView() {
    const [product, setProduct] = useState({});
    const { pseq } = useParams();
    const navigate = useNavigate();

    useEffect(
        () => {
            axios.get(`/api/admin/getProduct/${pseq}`)
                .then((result) => {
                    setProduct(result.data.product);
                    console.log(result.data.product);
                })
                .catch((err) => { console.error(err) })
        }, [])

        function getKind( kind ){
            if( kind=="1"){return 'HEELS'}
            if( kind=="2"){return 'BOOTS'}
            if( kind=="3"){return 'SANDALS'}
            if( kind=="4"){return 'SNEACKERS'}
            if( kind=="5"){return 'SLEEPERS'}
        }

        function onDeleteProduct(){
            if( window.confirm("이 상품을 삭제하시겠습니까?")){
                axios.delete(`/api/admin/deleteProduct/${pseq}`)
                .then(()=>{navigate("/productList")})
                .catch((err)=>{console.log(err)})
            }else{
                return
            }
        }
        
        

    return (
        <div className='adminContainer'>
            <SubMenu />
            <h2>Product View</h2>
            <div className='productTable'>
                <div className="field">
                    <label>카테고리</label><div>{getKind( product.kind)}</div>
                </div>
                <div className="field">
                    <label>상품명</label><div>{product.name}</div>
                </div>
                <div className="field">
                    <label>신상품</label><div>{(product.useyn=='Y')?(<div>예</div>):(<div>아니요</div>)}</div>
                </div>
                <div className="field">
                    <label>베스트상품</label><div>{(product.bestyn=='Y')?(<div>예</div>):(<div>아니요</div>)}</div>
                </div>
                <div className="field">
                    <label>원가</label><div>{product.price1}</div>
                </div>
                <div className="field">
                    <label>판매가</label><div>{product.price2}</div>
                </div>
                <div className="field">
                    <label>마진</label><div>{product.price3}</div>
                </div>
                <div className="field">
                    <label>상품설명</label><div>{product.content}</div>
                </div>
                <div className="field">
                    <label>이미지</label>
                    <div><img src={`http://localhost:8070/product_images/${product.savefilename}`} width="200" /></div>
                </div>
                <div className='btns'>
                    <button onClick={() => {navigate(`/updateProduct/${product.pseq}`)}}>수정</button>
                    <button onClick={()=>{onDeleteProduct()}}>삭제</button>
                    <button onClick={()=>{navigate('/ProductList')}}>돌아가기</button>
                </div>
            </div>
        </div>
    )
}

export default ProductView
