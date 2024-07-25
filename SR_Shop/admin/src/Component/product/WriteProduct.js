import React, { useState, useEffect } from 'react'
import axios from 'axios'
import SubMenu from '../SubMenu'
import { Link, useNavigate } from 'react-router-dom'
import '../../style/admin.css'


function WriteProduct() {

    const [kind, setKind] = useState();
    const [name, setName] = useState();
    const [price1, setPrice1] = useState();
    const [price2, setPrice2] = useState();
    const [price3, setPrice3] = useState();
    const [content, setContent] = useState();
    const [image, setImage] = useState();
    const [savefilename, setSavefilename] = useState();
    const [imgSrc, setImgSrc] = useState();
    const navigate = useNavigate();


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
        axios.post('/api/admin/insertProduct', { kind, name, price1, price2, price3, content, image, savefilename, useyn: 'Y', bestyn: 'N' })
            .then(() => {
                navigate('/productList')
            })
            .catch((err) => {
                console.error(err);
            })
    }

    return (
        <div className='adminContainer'>
            <SubMenu />
            <h2>상품 등록</h2>
            <div className='productTable'>
                <div className="field">
                    <label>상품종류</label>
                    <select style={{ flex: '5' }} onChange={(e) => { selected(e); }}>
                        <option value="">종류선택</option>
                        <option value="1">HEELS</option>
                        <option value="2">BOOTS</option>
                        <option value="3">SANDALS</option>
                        <option value="4">SNEACKERS</option>
                        <option value="5">SLEEPERS</option>
                    </select>

                </div>
                <div className="field">
                    <label>상품명</label>
                    <input type="text" value={name} onChange={(e) => { setName(e.currentTarget.value) }} />
                </div>
                <div className="field">
                    <label>원가</label>
                    <input type="text" id="price1" value={price1} onChange={
                        (e) => { setPrice1(e.currentTarget.value)
                            let p1 = document.getElementById("price1").value;
                            let p2 = document.getElementById("price2").value;
                            if( p2!='' && p1 !=''){
                                document.getElementById("price3").value = p2-p1
                                setPrice3(p2-p1);
                            }
                         }
                        } />
                </div>
                <div className="field">
                    <label>판매가</label>
                    <input type="text" id="price2" value={price2} onChange={
                        (e) => { setPrice2(e.currentTarget.value)
                            let p1 = document.getElementById("price1").value;
                            let p2 = document.getElementById("price2").value;
                            if( p2!='' && p1 !=''){
                                document.getElementById("price3").value = p2-p1
                                setPrice3(p2-p1);
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

export default WriteProduct
