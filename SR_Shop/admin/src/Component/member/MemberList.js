import React, { useState, useEffect } from 'react'
import SubMenu from '../SubMenu'
import axios from 'axios'
import { Link, useNavigate } from 'react-router-dom'
import '../../style/admin.css'


function MemberList() {
    const [memberList, setMemberList] = useState();
    const [paging, setPaging] = useState({});
    const navigate = useNavigate();

    useEffect(
        () => {
            axios.get('/api/admin/getMemberList/1')
                .then((result) => {
                    setMemberList(result.data.memberList)
                    setPaging(result.data.paging);
                })
                .catch((err) => { console.error(err) })
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
        axios.get(`/api/admin/getMemberList/${page}`)
            .then((result) => {
                setPaging(result.data.paging);
                let members = [];
                members = [...memberList];
                members = [...members, ...result.data.memberList];
                setMemberList([...members]);
            })
            .catch((err) => { console.error(err) })
    }

    async function changeUseyn(userid, ch) {
        let useyn = "Y";
        if (ch) { useyn = "N" }
        try {
            await axios.post('/api/admin/changeUseyn', null, { params: { userid, useyn } })

            const result = await axios.get('/api/admin/getMemberList/1')
            setMemberList(result.data.memberList)
            setPaging(result.data.paging);
        } catch (err) { console.error(err) }

    }



    return (
        <div className='adminContainer'>
            <SubMenu />
            <div className='btns' style={{ display: "flex", margin: "5px" }}>
                <input type="text" /><button>검색</button>
            </div>
            <div className='productTable'>
                <div className='row'>
                    <div className='col'>선택<br/>휴면/탈퇴</div>
                    <div className='col' style={{ flex: '1' }}>User ID</div>
                    <div className='col'>성명</div>
                    <div className='col'>Phone</div>
                    <div className='col' style={{ flex: '2' }} >배송주소</div>
                    <div className='col'>Provider</div>
                    <div className='col'>가입일</div>
                </div>
                {
                    (memberList) ? (
                        memberList.map((member, idx) => {
                            return (
                                <div className='row'>
                                    <div className='col'>
                                        {
                                            (member.useyn == "Y") ? (<input type="checkbox" value={member.userid} onChange={(e) => { changeUseyn(member.userid, e.currentTarget.checked) }} />) : (<input type="checkbox" value={member.userid} onChange={(e) => { changeUseyn(member.userid, e.currentTarget.checked) }} checked />)
                                        }
                                    </div>
                                    <div className='col' style={{ flex: '1' }}>
                                        {member.userid}
                                    </div>
                                    <div className='col'>{member.name}</div>
                                    <div className='col'>{member.phone}</div>
                                    <div className='col' style={{ flex: '2' }}>{member.address1} {member.address2}</div>
                                    <div className='col'>{member.provider}</div>
                                    <div className='col'>{member.indate.substring(0, 10)}</div>
                                </div>
                            )
                        })
                    ) : (<span>loading...</span>)
                }
            </div>

        </div>
    )
}

export default MemberList
