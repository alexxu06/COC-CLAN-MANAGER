import axios from "axios";
import { useOutletContext } from 'react-router-dom';
import { Container, Row, Col, Image, Modal, Card, Button, Spinner } from 'react-bootstrap';
import type { War, CurrentClan, OutletContextType } from "../types/index.ts";
import { DateTime } from 'luxon';
import { useState } from "react";
import '../index.css';
import 'bootstrap/dist/css/bootstrap.min.css';


export default function WarMemberList() {
  const { memberList, clanTag } = useOutletContext<OutletContextType>();
  const warHistoryLimit: number = 20;
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [showWarBtn, setShowWarBtn] = useState<boolean>(false);
  const [modalShow, setModalShow] = useState<boolean>(false);
  const [warHistory, setWarHistory] = useState<War[] | null>(null);

  const fetchWarHistory = (tag: string, limit: number): void => {
    setIsLoading(true);
    axios.get("/api/player-wars", { params: { tag: tag, limit: limit } })
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
      })
      .finally(() => {
        // this is to differentiate between first 20 vs all war history
        // so it can determine whether to show the button to show all war history
        if (limit > 999) {
          setShowWarBtn(false);
        }
        setIsLoading(false);
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

  const displayWarHistory = warHistory?.length ? (
    <>
      {warHistory.map((war, index) => {
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
                  {clan.name} <br />
                  {clan.tag}
                </div>
              </Col>
              <Col md="auto">
              </Col>
              <Col xs lg="3">
                <Card.Img variant="top" src={opp.badgeUrls.large} style={{ maxWidth: "6rem" }} />
                <div>
                  {opp.name} <br />
                  {opp.tag}
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
              <div className="text-center">
                {war.attacks.map((attack, index) =>
                  <Row key={index} className="justify-content-md-center text-center">
                    stars: {attack.stars} Destruction: {attack.destructionPercentage}
                  </Row>
                )}
              </div>

            </Card.Body>
          </Card>
        );
      })}
      {showWarBtn &&
        <div className="text-center m-4">
          <Button onClick={() => {
            fetchWarHistory(warHistory[0].member_data.tag, 9999)
          }}
            className="justify-center"
            disabled={isLoading}>
            Load All Available Wars
            {isLoading &&
              <Spinner
                className="ms-2"
                as="span"
                animation="border"
                size="sm"
                role="status"
                aria-hidden="true"
              />
            }
          </Button>
        </div>
      }
    </>
  ) : <p>no wars found</p>;

  const calculateAverage = (total: number, attacks: number) => {
    let average: number = Math.round(total / attacks * 100)
    return isNaN(average) ? 0 : average;
  }

  const displayWarStats = memberList.map(member =>
    <Row key={member.tag}
      className="align-items-center border-bottom py-2 hover-row"
      onClick={() => {
        fetchWarHistory(member.tag, warHistoryLimit);
        setShowWarBtn(true);
      }} >
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