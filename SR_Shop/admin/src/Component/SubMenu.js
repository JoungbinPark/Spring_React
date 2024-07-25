import React, {useState, useEffect} from 'react'
import {Link, useNavigate } from 'react-router-dom'

function SubMenu() {
  return (
    <div className="adminmenu">
        <Link to="/productList">PRODUCT</Link>
        <Link to="/orderList">ORDER</Link>
        <Link to="/memberList">MEMBER</Link>
        <Link to="/qnaList">Q&A</Link>
        
      
    </div>
  )
}

export default SubMenu
