package dungeon;

class LiMappings {

  static String getLocationImage(String li) {
    switch (li) {
      case "[NORTH]":
        return "./res/img/bw-cells/N.png";
      case "[EAST]":
        return "./res/img/bw-cells/E.png";
      case "[SOUTH]":
        return "./res/img/bw-cells/S.png";
      case "[WEST]":
        return "./res/img/bw-cells/W.png";
      case "[EAST, NORTH]":
        return "./res/img/bw-cells/NE.png";
      case "[EAST, SOUTH]":
        return "./res/img/bw-cells/SE.png";
      case "[EAST, WEST]":
        return "./res/img/bw-cells/EW.png";
      case "[NORTH, SOUTH]":
        return "./res/img/bw-cells/NS.png";
      case "[NORTH, WEST]":
        return "./res/img/bw-cells/NW.png";
      case "[SOUTH, WEST]":
        return "./res/img/bw-cells/SW.png";
      case "[EAST, NORTH, SOUTH]":
        return "./res/img/bw-cells/NSE.png";
      case "[EAST, NORTH, WEST]":
        return "./res/img/bw-cells/NEW.png";
      case "[EAST, SOUTH, WEST]":
        return "./res/img/bw-cells/SEW.png";
      case "[NORTH, SOUTH, WEST]":
        return "./res/img/bw-cells/NSW.png";
      case "[EAST, NORTH, SOUTH, WEST]":
        return "./res/img/bw-cells/NSEW.png";
      case "Player":
        return "./res/img/player.png";
      case "Valid Visited":
        return "./res/img/valid_visited.png";
      case "Valid Unvisited":
        return "./res/img/valid_unvisited.png";
      case "Petrichor":
        return "./res/img/petrichor.png";
      case "Less Stench":
        return "./res/img/stench01.png";
      case "More Stench":
        return "./res/img/stench02.png";
      case "Pit":
        return "./res/img/pit.png";
      case "Otyugh":
        return "./res/img/otyugh.png";
      case "Thief":
        return "./res/img/thief.png";
      case "Beholder":
        return "./res/img/beholder.png";
      case "D-Pad":
        return "./res/img/d_pad.png";
      case "Health":
        return "./res/img/health.png";
      case "Ruby":
        return "./res/img/ruby.png";
      case "Sapphire":
        return "./res/img/sapphire.png";
      case "Diamond":
        return "./res/img/diamond.png";
      case "White Arrow":
        return "./res/img/arrow-white.png";
      case "Black Arrow":
        return "./res/img/arrow-black.png";
      case "Weapon 2":
        return "./res/img/weapon_2.png";
      case "Weapon 3":
        return "./res/img/weapon_3.png";
      case "Blood":
        return "./res/img/blood.png";
      case "Background":
        return "./res/img/background.png";
      case "Blank":
      default:
        return "./res/img/blank.png";
    }
  }
}
