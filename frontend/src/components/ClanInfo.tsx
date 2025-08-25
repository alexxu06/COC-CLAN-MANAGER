import Card from 'react-bootstrap/Card';
import { type Clan } from '../types';

type ClanInfoProp = {
  clan: Clan | null;
}

export default function ClanInfo({ clan }: ClanInfoProp) {
  return (
    <Card className='mt-3'>
      <Card.Img variant="top" 
      src={clan?.clanImg} 
      className='mx-auto img-fluid mt-4'
      style={{ maxWidth: '35%', height: 'auto' }}/>
      <Card.Body>
        <Card.Title className='text-center'>{clan?.name}</Card.Title>
        <Card.Text className='text-center'>{clan?.tag}</Card.Text>
        <Card.Text className='text-center'>
          {clan?.description}
        </Card.Text>
      </Card.Body>
    </Card>
  );
}
