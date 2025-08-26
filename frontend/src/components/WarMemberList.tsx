import axios from "axios";
import { Container, Row, Col, Image, Modal, Card } from 'react-bootstrap';
import type { Member, War, CurrentClan } from "../types/index.ts";
import { DateTime } from 'luxon';
import { useState } from "react";
import '../index.css';
import 'bootstrap/dist/css/bootstrap.min.css';

type MemberListProp = {
  memberList: Member[];
  clanTag: string;
}

export default function WarMemberList({ memberList, clanTag }: MemberListProp) {
  const [modalShow, setModalShow] = useState<boolean>(false);
  const [warHistory, setWarHistory] = useState<War[] | null>(null);

  const fetchWarHistory = (tag: string): void => {
    axios.get("/api/player-wars", { params: { tag: tag } })
      .then((response) => {
        setWarHistory(response.data);
        setModalShow(true);
        console.log(response.data)
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

  // sometimes it clan and opp are swapped due to how external api stores wars
  const getCorrectClan = (clan: CurrentClan, opp: CurrentClan): CurrentClan => {
    return clan.tag == clanTag ? clan : opp;
  }

  const getCorrectOpp = (clan: CurrentClan, opp: CurrentClan): CurrentClan => {
    return opp.tag != clanTag ? opp : clan;
  }

  const parseDate = (date: string): string => {
    const dt = DateTime.fromFormat(date, "yyyyMMdd'T'HHmmss.SSS'Z'", { zone: 'utc' });
    return dt.toFormat("yyyy-MM-dd HH:mm:ss");
  }

  const displayWarHistory = warHistory?.length ? warHistory.map((war, index) => {
    let clan: CurrentClan = getCorrectClan(war.war_data.clan, war.war_data.opponent)
    let opp: CurrentClan = getCorrectOpp(war.war_data.clan, war.war_data.opponent)

    // player was in different clan at this point of time
    if (clan.tag == opp.tag) {
      clan = war.war_data.clan;
    }

    return (
      <Card key={index}>
        <Row className="justify-content-md-center text-center">
          <Col xs lg="3">
            <Card.Img variant="top" src={clan.badgeUrls.large} style={{ maxWidth: "6rem" }} />
            <div>
              {clan.name}
            </div>
          </Col>
          <Col md="auto">
          </Col>
          <Col xs lg="3">
            <Card.Img variant="top" src={opp.badgeUrls.large} style={{ maxWidth: "6rem" }} />
            <div>
              {opp.name}
            </div>
          </Col>
        </Row>
        <Card.Body>
          <Card.Text className="text-center">
            <b>type:</b> {war.war_data.type == "random" ? "normal" : war.war_data.type}
          </Card.Text>
          <Card.Text className="text-center">
            <b>start:</b> {parseDate(war.war_data.startTime)} (UTC)
          </Card.Text>
          <Card.Text className="text-center">
            <b>end:</b> {parseDate(war.war_data.endTime)} (UTC)
          </Card.Text>
          <Card.Text className="text-center">
            <b>attacks</b> ({war.attacks.length}/{war.war_data.type == "random" ? 2 : 1}):
          </Card.Text>
          <Card.Text className="text-center">
            {war.attacks.map((attack, index) =>
              <Row key={index} className="justify-content-md-center text-center">
                stars: {attack.stars} Destruction: {attack.destructionPercentage}
              </Row>
            )}
          </Card.Text>

        </Card.Body>
      </Card>
    )
  }
  ) : <p>no wars found</p>;

  const calculateAverage = (total: number, attacks: number) => {
    let average: number = Math.round(total / attacks * 100)
    return isNaN(average) ? 0 : average;
  }

  const displayWarStats = memberList.map(member =>
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

      {displayWarStats}

      <Modal
        size="lg"
        show={modalShow}
        onHide={() => setModalShow(false)}
        aria-labelledby="example-modal-sizes-title-lg"
      >
        <Modal.Header closeButton>
          <Modal.Title id="example-modal-sizes-title-lg">
            Wars
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>{displayWarHistory}</Modal.Body>
      </Modal>
    </Container>
  );
}