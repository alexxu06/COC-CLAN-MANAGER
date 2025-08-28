import { Container, Nav } from 'react-bootstrap';
import { Link, useLocation, useParams } from 'react-router-dom';
import '../index.css';
import 'bootstrap/dist/css/bootstrap.min.css';

export default function NavBar() {
  const location = useLocation();
  const { clanTag } = useParams<{ clanTag: string }>();

  return (
    <Container className="mt-sm-0 mt-4">
      <Nav variant="tabs" activeKey={location.pathname} className={!clanTag ? "disabled" : ""}>
        <Nav.Item>
          <Nav.Link as={Link} to="general" eventKey={clanTag ? `/${clanTag}/general` : "disabled"} disabled={!clanTag}>
            General
          </Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link as={Link} to="war" eventKey={clanTag ? `/${clanTag}/war` : "disabled" } disabled={!clanTag}>War</Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link eventKey="disabled" disabled>
            Clan Capital (coming soon)
          </Nav.Link>
        </Nav.Item>
      </Nav>
    </Container>
  );
}