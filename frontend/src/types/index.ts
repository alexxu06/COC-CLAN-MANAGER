export interface Clan {
    tag: string;
    name: string;
    clanImg: string;
    description: string;
    memberList: Member[];
}

export interface Member {
    name: string;
    tag: string;
    role: string;
    donations: number;
    clanRank: number;
    townHallLevel: number;
    trophies: number;
    totalStars: number;
    totalPercentage: number;
    numAttacks: number;
    totalAttacks: number;
}

export interface War {
  attacks: Attack[]
  war_data: WarData
}

export interface Attack {
  stars: number
  destructionPercentage: number
  defender: Defender
}

export interface Defender {
  tag: string
  name: string
  townhallLevel: string
  mapPosition: string
  opponentAttacks: string
}

export interface WarData {
  teamSize: number
  startTime: string
  endTime: string
  clan: CurrentClan
  opponent: Opponent
  type: string
}

export interface CurrentClan {
  tag: string
  name: string
  badgeUrls: BadgeUrls
  clanLevel: number
  stars: number
  destructionPercentage: number
}

export interface BadgeUrls {
  small: string
  large: string
  medium: string
}

export interface Opponent {
  tag: string
  name: string
  badgeUrls: BadgeUrls2
  clanLevel: number
  stars: number
  destructionPercentage: number
}

export interface BadgeUrls2 {
  small: string
  large: string
  medium: string
}
