import SearchBar from "./components/SearchBar";
import ClanInfo from "./components/ClanInfo";
import WarMemberList from "./components/WarMemberList";
import GeneralMemberList from "./components/GeneralMemberList";
import NavBar from './components/NavBar.tsx';
import { useState } from "react";
import { Container, Row, Col } from "react-bootstrap";
import { type Clan } from "./types"
import { Routes, Route, Navigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  const [clan, setClan] = useState<Clan | null>(null);

  return (
    <Container className="mt-5">
      <Row>
        <Col md={5}>
          <SearchBar setClan={setClan} />
          <ClanInfo clan={clan} />
        </Col>
        <Col md={7}>
        <Container className="mt-sm-0 mt-4">
          <NavBar />
          <Routes>
            <Route path="/general" element={<GeneralMemberList memberList={clan?.memberList ?? []} />} />
            <Route path="/war" element={<WarMemberList memberList={clan?.memberList ?? []} />} />
          </Routes>
        </Container>
        </Col>
      </Row>
    </Container>
  )
}

export default App
