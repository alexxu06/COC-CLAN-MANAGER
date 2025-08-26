import SearchBar from "./components/SearchBar";
import ClanInfo from "./components/ClanInfo";
import WarMemberList from "./components/WarMemberList";
import GeneralMemberList from "./components/GeneralMemberList";
import NavBar from './components/NavBar.tsx';
import { useState } from "react";
import { Container, Row, Col, Spinner } from "react-bootstrap";
import { type Clan } from "./types"
import { Routes, Route } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  const [clan, setClan] = useState<Clan | null>(null);
  const [isMembersLoading, setMembersLoading] = useState<boolean>(false);

  return (
    <Container className="mt-5">
      <Row>
        <Col md={5}>
          <SearchBar setClan={setClan} setLoading={setMembersLoading} />
          <ClanInfo clan={clan} />
        </Col>
        <Col md={7}>
          <Container className="mt-sm-0 mt-4">
            <NavBar />
            {isMembersLoading ? (
              <div className="d-flex justify-content-center" >
                <Spinner animation="border" className="mt-4"
                style={ {width: "9rem", height: "9rem", borderWidth: '0.7rem', color: 'rgba(0, 0, 0 , 0.2)' } }/>
                </div>): (
              <Routes>
            <Route path = "/general" element = { <GeneralMemberList memberList = {clan?.memberList ?? []} />} />
            <Route path="/war" element={<WarMemberList memberList={clan?.memberList ?? []} clanTag={clan?.tag ?? ""} />} />
          </Routes>
        )}
        </Container>
      </Col>
    </Row>
    </Container >
  )
}

export default App
