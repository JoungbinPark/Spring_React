import React, { useState, useEffect } from 'react'
import axios from 'axios'
import SubMenu from '../SubMenu'
import { useNavigate, useParams } from 'react-router-dom'
import '../../style/admin.css'

function UpdateProduct() {

    const { pseq } = useParams();
    const [kind, setKind] = useState();
    const [name, setName] = useState();
    const [price1, setPrice1] = useState();
    const [price2, setPrice2] = useState();
    const [price3, setPrice3] = useState();
    const [content, setContent] = useState();
    const [image, setImage] = useState();
    const [savefilename, setSavefilename] = useState();
    const [imgSrc, setImgSrc] = useState();
    const [useyn, setUseyn] = useState();
    const [bestyn, setBestyn] = useState();
    const [oldImage, setOldImage] = useState();
    const navigate = useNavigate();


    useEffect(
        () => {
            axios.get(`/api/admin/getProduct/${pseq}`)
                .then((result) => {
                    setKind(result.data.product.kind);
                    setName(result.data.product.name);
                    setPrice1(result.data.product.price1);
                    setPrice2(result.data.product.price2);
                    setPrice3(result.data.product.price3);
                    setContent(result.data.product.content);
                    setImage(result.data.product.image);
                    setSavefilename(result.data.product.savefilename);
                    setUseyn(result.data.product.useyn);
                    setBestyn(result.data.product.bestyn);
                    setOldImage(`http://localhost:8070/product_images/${result.data.product.savefilename}`);
                })
                .catch((err) => { console.error(err) })
        }, []
    )


    function selected(e) {
        setKind(e.target.value);
    }

    function fileup(e) {
        const formData = new FormData();
        formData.append('image', e.target.files[0]);
        axios.post(`/api/admin/fileup`, formData)
            .then((result) => {
                setImage(result.data.image);
                setSavefilename(result.data.savefilename);
                setImgSrc(`http://localhost:8070/product_images/${result.data.savefilename}`)
            })
            .catch((err) => { console.error(err) })

    }

    function onSubmit() {
        axios.post('/api/admin/updateProduct', { kind, name, price1, price2, price3, content, image, savefilename, useyn, bestyn, pseq })
            .then(() => {
                navigate(`/productView/${pseq}`)
            })
            .catch((err) => {
                console.error(err);
            })
    }

    return (
        <div className='adminContainer'>
            <SubMenu />
            <h2>상품 수정</h2>
            <div className='productTable'>
                <div className="field">
                    <label>상품종류</label>
                    <select style={{ flex: '5' }} onChange={(e) => { selected(e); }}>
                        <option value="">종류선택</option>
                        {(kind == '1') ? (<option value='1' selected>HEELS</option>) : (<option value='1'>HEELS</option>)}
                        {(kind == '2') ? (<option value='2' selected>BOOTS</option>) : (<option value='2'>BOOTS</option>)}
                        {(kind == '3') ? (<option value='3' selected>SANDALS</option>) : (<option value='3'>SANDALS</option>)}
                        {(kind == '4') ? (<option value='4' selected>SNEACKERS</option>) : (<option value='4'>SNEACKERS</option>)}
                        {(kind == '5') ? (<option value='5' selected>SLEEPERS</option>) : (<option value='5'>SLEEPERS</option>)}
                    </select>

                </div>
                <div className="field">
                    <label>상품명</label>
                    <input type="text" value={name} onChange={(e) => { setName(e.currentTarget.value) }} />
                </div>
                <div className="field">
                    <label>신상품</label>
                    {(useyn == 'Y') ? (
                        <div>
                            <input name='useyn' type="radio" value='Y' 
                            onClick={(e)=>{setUseyn(e.target.value)}} checked />예
                            <input name='useyn' type="radio" value='N'
                            onClick={(e)=>{setUseyn(e.target.value)}}  /> 아니요
                        </div>
                    ) : (
                        <div>
                            <input name='useyn' type="radio" value='Y'
                            onClick={(e)=>{setUseyn(e.target.value)}}  /> 예
                            <input name='useyn' type="radio" value='N' checked 
                            onClick={(e)=>{setUseyn(e.target.value)}} /> 아니요
                        </div>
                    )
                    }
                </div>
                <div className="field">
                    <label>베스트</label>
                    {(bestyn == 'Y') ? (
                        <div>
                            <input name='bestyn' type="radio" value='Y'
                            onClick={(e)=>{setBestyn(e.target.value)}} checked />예
                            <input name='bestyn' type="radio" value='N'
                            onClick={(e)=>{setBestyn(e.target.value)}} /> 아니요
                        </div>
                    ) : (
                        <div>
                            <input name='bestyn' type="radio" value='Y' 
                            onClick={(e)=>{setBestyn(e.target.value)}} /> 예
                            <input name='bestyn' type="radio" value='N' 
                            onClick={(e)=>{setBestyn(e.target.value)}} checked /> 아니요
                        </div>
                    )
                    }
                </div>

                <div className="field">
                    <label>원가</label>
                    <input type="text" id="price1" value={price1} onChange={
                        (e) => {
                            setPrice1(e.currentTarget.value)
                            let p1 = document.getElementById("price1").value;
                            let p2 = document.getElementById("price2").value;
                            if (p2 != '' && p1 != '') {
                                document.getElementById("price3").value = p2 - p1
                                setPrice3(p2 - p1);
                            }
                        }
                    } />
                </div>
                <div className="field">
                    <label>판매가</label>
                    <input type="text" id="price2" value={price2} onChange={
                        (e) => {
                            setPrice2(e.currentTarget.value)
                            let p1 = document.getElementById("price1").value;
                            let p2 = document.getElementById("price2").value;
                            if (p2 != '' && p1 != '') {
                                document.getElementById("price3").value = p2 - p1
                                setPrice3(p2 - p1);
                            }
                        }
                    } />
                </div>
                <div className="field">
                    <label>마진</label>
                    <input type="text" id="price3" value={price3} onChange={
                        (e) => { setPrice3(e.currentTarget.value) }
                    } readOnly />
                </div>
                <div className="field">
                    <label>상품설명</label>
                    <input type="text" value={content} onChange={(e) => { setContent(e.currentTarget.value) }} />
                </div>

                <div className="field">
                    <label>기존 미리보기</label>
                    <div><img src={oldImage} width="200" /></div>
                </div>

                <div className="field">
                    <label>이미지</label>
                    <input type="file" onChange={(e) => { fileup(e) }} />
                </div>
                <div className="field">
                    <label>이미지 미리보기</label>
                    <div><img src={imgSrc} width="200" /></div>
                </div>
                <div className='btns'>
                    <button onClick={() => { onSubmit(); }}>상품등록</button>
                    <button onClick={() => { navigate('/productList'); }}>돌아가기</button>
                </div>
            </div>
        </div>
    )
}

export default UpdateProduct
