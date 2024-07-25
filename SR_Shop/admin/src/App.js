import { Routes, Route } from 'react-router-dom'
import Admin from './Component/Admin'
import ProductList from './Component/product/ProductList'
import WriteProduct from './Component/product/WriteProduct'
import UpdateProduct from './Component/product/UpdateProduct'
import ProductView from './Component/product/ProductView'
import OrderList from './Component/order/OrderList'
import MemberList from './Component/member/MemberList'
import QnaList from './Component/qna/QnaList'
import QnaView from './Component/qna/QnaView'


function App() {

  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Admin />} />
        <Route path="/productList" element={<ProductList />} />
        <Route path="/orderList" element={<OrderList />} />
        <Route path="/qnaList" element={<QnaList />} />
        <Route path="/memberList" element={<MemberList />} />
        <Route path="/writeProduct" element={<WriteProduct />} />
        <Route path="/productView/:pseq" element={<ProductView />} />
        <Route path="/updateProduct/:pseq" element={<UpdateProduct />} />
        <Route path="/qnaView/:qseq" element={<QnaView />} />
        qnaView
        
      </Routes>
    </div>
  );
}

export default App;
