import React, { useState, useEffect } from 'react'
import axios from 'axios'

import { useNavigate } from "react-router-dom";

import '../style/admin.css'


function Admin() {
    const [adminid, setAdminid] = useState();
    const [pwd, setPwd] = useState();

    const navigate = useNavigate();


    function onLogin() {
        if (!adminid) { return alert("아이디를 입력하세요") }
        if (!pwd) { return alert("패스워드를 입력하세요") }
        axios.post('/api/admin/login', { adminid, pwd })
            .then((result) => {
                if (result.data.msg == "OK") {
                    navigate(`/productList`);
                } else {
                    return alert(result.data.msg);
                }
            })
            .catch((err) => { console.error(err) })
    };

    return (
        <div className='AdminForm'>
            <h2>Admin LogIn</h2>
            <div className="field">
                <label>Admin ID</label>
                <input type="text" value={adminid} onChange={(e) => {
                    setAdminid(e.currentTarget.value);
                }} />
            </div>
            <div className="field">
                <label>Password</label>
                <input type="password" value={pwd} onChange={(e) => {
                    setPwd(e.currentTarget.value);
                }} />
            </div>
            <div className="btns">
                <button onClick={() => { onLogin(); }}>로그인</button>
            </div>
        </div>
    )
}

export default Admin
