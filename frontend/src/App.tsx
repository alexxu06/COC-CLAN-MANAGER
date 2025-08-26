import WarMemberList from "./components/WarMemberList";
import GeneralMemberList from "./components/GeneralMemberList";
import ClanPage from "./components/ClanPage.tsx";
import { Routes, Route } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {

  return (
    <Routes>
      <Route path="/" element={<ClanPage />} />

      <Route path=":clanTag" element={<ClanPage />}>
        <Route index element={<GeneralMemberList />} />
        <Route path="general" element={<GeneralMemberList />} />
        <Route path="war" element={<WarMemberList />} />
      </Route>
    </Routes>
  )
}

export default App
