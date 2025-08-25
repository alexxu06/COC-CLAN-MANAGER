import axios from "axios";
import { Container, Row, Col, Image } from 'react-bootstrap';
import { type Member } from "../types/index.ts";
import '../index.css';
import 'bootstrap/dist/css/bootstrap.min.css';

type MemberListProp = {
  memberList: Member[];
}

export default function WarMemberList({ memberList }: MemberListProp) {

  const fetchWarHistory = (tag: string) => {
        axios.get("/api/player-wars", { params: { tag: tag } })
            .then((response) => {
                console.log(response.data);
            })
            .catch((error) => {
                if (error.response.status == 404) {
                    alert("Player Not Found");
                } else {
                    alert("System Error");
                }
                console.log(error);

            });
    }

  const calculateAverage = (total: number, attacks: number) => {
    let average: number = Math.round(total / attacks * 100)
    return isNaN(average) ? 0 : average;
  }

  const displayWar = memberList.map(member =>
    <Row key={member.tag}
      className="align-items-center border-bottom py-2 hover-row"
      onClick={() => fetchWarHistory(member.tag)} >
      <Col xs="auto" className='d-none d-sm-block'>
        <Image src={`/th${member.townHallLevel}.png`}
          fluid
          style={{ maxHeight: '50px', width: 'auto' }} />
      </Col>
      <Col xs={3}><strong>{member.name}</strong></Col>
      <Col xs={2} className='text-center'>
        {calculateAverage(member.totalStars, member.numAttacks) / 100}
      </Col>
      <Col xs={3} className='text-center'>
        {calculateAverage(member.totalPercentage, member.numAttacks) / 100}%
      </Col>
      <Col xs={2} className='text-center'>
        {calculateAverage(member.numAttacks, member.totalAttacks)}%
      </Col>
    </Row>
  );

  return (
    <Container className="mt-sm-0 mt-4">
      <Row className="align-items-center border-bottom" >
        <Col xs="auto" className='d-none d-sm-block'>
          <Image src="/th17.png"
            fluid
            style={{ maxHeight: '50px', width: 'auto' }}
            className='invisible' />
        </Col>
        <Col xs={3}>Name</Col>
        <Col xs={2} className='text-center'>Avg Stars</Col>
        <Col xs={3} className='text-center'>Avg Destruction</Col>
        <Col xs={2} className='text-center'>Hit Rate</Col>
      </Row>
      {displayWar}
    </Container>
  );
}