/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cricket.teamselector.data;

import com.cricket.teamselector.app.Gens;
import com.cricket.teamselector.entity.Player;
import com.cricket.teamselector.utils.PlayerListItem;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ZenitH
 */
public class StatisticsProvider implements Statistics {

    // method to truncate the filled temp_DB calsses and write one by one
    @Override
    public void setTemporaryPlayerRecords(List<String> playerCodes, List<String> years, String RecordType) throws Exception {
        try {
            CallableStatement truncateStatment;
            DBConnection ob = new DBConnection();
            Connection con = ob.createConnection();

            // truncates the current table
            truncateStatment = con.prepareCall("{call SP_TruncateTemporaryTables}");
            truncateStatment.executeQuery();

            //checks the player code list can have multiple same code cuz year wise
            //entries "playerCodes"
            for (String playerCode : playerCodes) {
                List<Player> playersList = new ArrayList();
                for (String year : years) {
                    Player p = getPlayerDetails(playerCode, year, RecordType);
                    // below getProjectDetails method will return  Player objects based on the number of years selected
                    if (p != null) {
                        playersList.add(p);
                    }

                }
                // sets a list of player objects which will caryy the same player code 
                // to be written as one entry where the data will be formated accordingly Ex avg/yers of stats
                calculateStats(playersList);
            }
        } catch (Exception ex) {
            System.out.println(ex);
            throw ex;
        }
    }

    // will get a player object based on the player code, year of stat, and record type
    @Override
    public Player getPlayerDetails(String playerCode, String year, String recordType) throws Exception {
        Player player = null;
        ResultSet res = null;
        try {
            CallableStatement cs;
            DBConnection ob = new DBConnection();
            Connection con = ob.createConnection();
            cs = con.prepareCall("{call SP_GetStatsByPlayerCodeYearNRecordType(?,?,?)}");
            cs.setString(1, playerCode);
            cs.setString(2, year);
            cs.setString(3, recordType);
            res = cs.executeQuery();

            while (res.next()) {
                player = new Player();
                player.setPlayerID(Integer.parseInt(res.getString(1)));
                player.setPlayerCode(res.getInt(2));
                player.setName(res.getString(3).trim());
                player.setPlayerRole(res.getString(4).trim());
                player.setNationality(res.getString(5).trim());
                player.setRecordType(res.getString(6).trim());
                player.setSoldValue(res.getInt(7));
                player.setBaseValue(res.getInt(8));
                player.setYear(res.getInt(9));
                player.setMatches(res.getInt(10));
                player.setBattedInnings(res.getInt(11));
                player.setNotOuts(res.getInt(12));
                player.setRunsScored(res.getInt(13));
                player.setHs(res.getInt(14));
                player.setBattingAve(res.getDouble(15));
                player.setBf(res.getInt(16));
                player.setBattingSr(res.getDouble(17));
                player.setHundreds(res.getInt(18));
                player.setFifties(res.getInt(19));
                player.setFours(res.getInt(20));
                player.setSixes(res.getInt(21));
                player.setCt(res.getInt(22));
                player.setStumpings(res.getInt(23));
                player.setBallsBowled(res.getInt(24));
                player.setRunsConceeded(res.getInt(25));
                player.setWkts(res.getInt(26));
                player.setBowlingAve(res.getDouble(27));
                player.setBowlingEcon(res.getDouble(28));
                player.setBowlingSr(res.getDouble(29));
                player.setFourwick(res.getInt(30));
                player.setFivewick(res.getInt(31));
            }
            return player;

        } catch (Exception ex) {
            throw ex;
        }

    }

    // Constructs the player object to be saved as one entry to the temporary table 
    @Override
    public void calculateStats(List<Player> playerWiseList) throws Exception {
        //base int is used to get the average of the added stats Ex :- 3 year averages are divided by 3 and taken
        //
        int base = playerWiseList.size();

        Player playerDetails = new Player();

        int playerID;
        int playerCode;
        String name;
        String playerRole;
        String nationality;
        String recordType;
        int soldValue = 0;
        int baseValue = 0;
        int year;
        int matches = 0;
        int battedInnings = 0;
        int notOuts = 0;
        int runsScored = 0;
        int hs = 0;
        double battingAve = 0;
        int bf = 0;
        double battingSr = 0;
        int hundreds = 0;
        int fifties = 0;
        int fours = 0;
        int sixes = 0;
        int ct = 0;
        int stumpings = 0;
        int ballsBowled = 0;
        int runsConceeded = 0;
        int wkts = 0;
        double bowlingAve = 0;
        double bowlingEcon = 0;
        double bowlingSr = 0;
        int fourwick = 0;
        int fivewick = 0;

        try {

            for (Player player : playerWiseList) {
                playerDetails.setPlayerRole(player.getPlayerRole());
                playerDetails.setPlayerCode(player.getPlayerCode());
                playerDetails.setRecordType(player.getRecordType());
                playerDetails.setYear(player.getYear());
                playerDetails.setName(player.getName());
                playerDetails.setNationality(player.getNationality());
                matches += player.getMatches();
                battedInnings += player.getBattedInnings();
                notOuts += player.getNotOuts();
                runsScored += player.getRunsScored();
                soldValue = player.getSoldValue();
                baseValue = player.getBaseValue();
                hs += player.getHs();
                battingAve += player.getBattingAve();
                bf += player.getBf();
                battingSr += player.getBattingSr();
                hundreds += player.getHundreds();
                fifties += player.getFifties();
                fours += player.getFours();
                sixes += player.getSixes();
                ct += player.getCt();
                stumpings += player.getStumpings();
                ballsBowled += player.getBallsBowled();
                runsConceeded += player.getRunsConceeded();
                wkts += player.getWkts();
                bowlingAve += player.getBowlingAve();
                bowlingEcon += player.getBowlingEcon();
                player.getBowlingSr();
                bowlingSr += player.getBowlingSr();;
                fourwick += player.getFourwick();
                fivewick += player.getFivewick();

            }
            playerDetails.setMatches(matches);
            playerDetails.setBattedInnings(battedInnings);
            playerDetails.setSoldValue(soldValue);
            playerDetails.setBaseValue(baseValue);
            playerDetails.setNotOuts(notOuts);
            playerDetails.setRunsScored(runsScored);
            playerDetails.setHs(hs);
            playerDetails.setBattingAve(battingAve / base);
            playerDetails.setBf(bf);
            playerDetails.setBattingSr(battingSr / base);
            playerDetails.setHundreds(hundreds);
            playerDetails.setFifties(fifties);
            playerDetails.setFours(fours);
            playerDetails.setSixes(sixes);
            playerDetails.setCt(ct);
            playerDetails.setStumpings(stumpings);
            playerDetails.setBallsBowled(ballsBowled);
            playerDetails.setRunsConceeded(runsConceeded);
            playerDetails.setWkts(wkts);
            playerDetails.setBowlingAve(bowlingAve / base);
            playerDetails.setBowlingEcon(bowlingEcon / base);
            playerDetails.setBowlingSr(bowlingSr / base);
            playerDetails.setFourwick(fourwick);
            playerDetails.setFivewick(fivewick);
            setPlayerDetailsToTemporaryTable(playerDetails);
        } catch (Exception ex) {
            throw ex;
        }
    }

    // depending on the player type the respective SP is called to add data
    @Override
    public void setPlayerDetailsToTemporaryTable(Player player) {
        String playerType = player.getPlayerRole();

        try {
            switch (playerType) {
                case "Batsman": {
                    addToTemporaryTable("SP_AddToTemporaryBatsman", player);

                    break;
                }
                case "Bowler": {
                    addToTemporaryTable("SP_AddToTemporaryBowler", player);
                    break;
                }
                case "Wicketkeeper": {
                    addToTemporaryTable("SP_AddToTemporaryWicketKeeper", player);
                    break;
                }
                case "Allrounder": {
//                    System.out.println("Adding all-rounders to the DB");
                    addToTemporaryTable("SP_AddToTemporaryAllrounder", player);
                    break;
                }
            }
        } catch (Exception ex) {
        }

    }

    // adding players to the temporary table by type helper method to "setPlayerDetailsToTemporaryTable" method
    @Override
    public void addToTemporaryTable(String spName, Player player) throws Exception {
        try {
            CallableStatement cs;
            DBConnection ob = new DBConnection();
            Connection con = ob.createConnection();
            // calling the SP which is set by "setPlayerDetailsToTemporaryTable" method to insert players accordingly
            cs = con.prepareCall("{call " + spName + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

            cs.setInt(1, player.getPlayerCode());
            cs.setString(2, player.getName());
            cs.setString(3, player.getPlayerRole());
            cs.setString(4, player.getNationality());
            cs.setString(5, player.getRecordType());
            cs.setInt(6, player.getSoldValue());
            cs.setInt(7, player.getBaseValue());
            cs.setInt(8, player.getYear());
            cs.setInt(9, player.getMatches());
            cs.setInt(10, player.getBattedInnings());
            cs.setInt(11, player.getNotOuts());
            cs.setInt(12, player.getRunsScored());
            cs.setInt(13, player.getHs());
            cs.setDouble(14, player.getBattingAve());
            cs.setInt(15, player.getBf());
            cs.setDouble(16, player.getBattingSr());
            cs.setInt(17, player.getHundreds());
            cs.setInt(18, player.getFifties());
            cs.setInt(19, player.getFours());
            cs.setInt(20, player.getSixes());
            cs.setInt(21, player.getCt());
            cs.setInt(22, player.getStumpings());
            cs.setInt(23, player.getBallsBowled());
            cs.setInt(24, player.getRunsConceeded());
            cs.setInt(25, player.getWkts());
            cs.setDouble(26, player.getBowlingAve());
            cs.setDouble(27, player.getBowlingEcon());
            cs.setDouble(28, player.getBowlingSr());
            cs.setInt(29, player.getFourwick());
            cs.setInt(30, player.getFivewick());

            cs.executeUpdate();
//            int i =cs.executeUpdate();
//            System.out.println(i);

        } catch (Exception ex) {
            System.err.println("Temp Insert Exception : " + ex);
            throw ex;
        }

    }

    @Override
    public int getBatsmanIDSize() {
        ResultSet res = null;
        int size = 0;
        try {
            CallableStatement cs;
            DBConnection ob = new DBConnection();
            Connection con = ob.createConnection();
            cs = con.prepareCall("{call SP_GetTempBatsmanIDSize}");
            res = cs.executeQuery();
            while (res.next()) {
                size = res.getInt(1);
            }
        } catch (Exception ex) {
            String msg = ex.getMessage();
        }
        return size;

    }

    @Override
    public int getBowlerIDSize() {
        ResultSet res = null;
        int size = 0;
        try {
            CallableStatement cs;
            DBConnection ob = new DBConnection();
            Connection con = ob.createConnection();
            cs = con.prepareCall("{call SP_GetTempBowlingIDSize}");
            res = cs.executeQuery();
            while (res.next()) {
                size = res.getInt(1);
            }
        } catch (Exception ex) {
            String msg = ex.getMessage();
        }
        return size;

    }

    @Override
    public int getWicketKeeperIDSize() {
        ResultSet res = null;
        int size = 0;
        try {
            CallableStatement cs;
            DBConnection ob = new DBConnection();
            Connection con = ob.createConnection();
            cs = con.prepareCall("{call SP_GetTempWicketkeeperIDSize}");
            res = cs.executeQuery();
            while (res.next()) {
                size = res.getInt(1);
            }
        } catch (Exception ex) {
            String msg = ex.getMessage();
        }
        return size;

    }

    @Override
    public int getAllrounderIDSize() {
        ResultSet res = null;
        int size = 0;
        try {
            CallableStatement cs;
            DBConnection ob = new DBConnection();
            Connection con = ob.createConnection();
            cs = con.prepareCall("{call SP_GetTempAllrounderIDSize}");
            res = cs.executeQuery();
            while (res.next()) {
                size = res.getInt(1);
            }
        } catch (Exception ex) {
            String msg = ex.getMessage();
        }
        return size;

    }

    @Override
    public Player getAllrounderById(int playerID) throws Exception {

        Player player = new Player();
        ResultSet res = null;
        try {
            CallableStatement cs;
            DBConnection ob = new DBConnection();
            Connection con = ob.createConnection();
            cs = con.prepareCall("{call SP_GetAllrounderByID(?)}");
            cs.setInt(1, playerID);
            res = cs.executeQuery();

            while (res.next()) {
                player.setPlayerID(res.getInt(1));
                player.setPlayerCode(res.getInt(2));
                player.setName(res.getString(3).trim());
                player.setPlayerRole(res.getString(4).trim());
                player.setNationality(res.getString(5).trim());

                player.setRecordType(res.getString(6).trim());
                player.setSoldValue(res.getInt(7));
                player.setBaseValue(res.getInt(8));
                player.setYear(res.getInt(9));
                player.setMatches(res.getInt(10));

                player.setBattedInnings(res.getInt(11));
                //player.setInnings_played(res.getString(10).trim());
                player.setNotOuts(res.getInt(12));
                player.setRunsScored(res.getInt(13));
                player.setHs(res.getInt(14));
                // player.setIs_star_batsman(res.getString(14).trim());
                player.setBattingAve(res.getDouble(15));

                player.setBf(res.getInt(16));
                player.setBattingSr(res.getDouble(17));
                player.setHundreds(res.getInt(18));
                player.setFifties(res.getInt(19));
                player.setFours(res.getInt(20));

                player.setSixes(res.getInt(21));

                //player.setCt(res.getString(22).trim());
                player.setCt(res.getInt(22));

                player.setStumpings(res.getInt(23));
                player.setBallsBowled(res.getInt(24));
                player.setRunsConceeded(res.getInt(25));

                //player.setIs_star_bowler(res.getString(27).trim());
                player.setWkts(res.getInt(26));
                player.setBowlingAve(res.getDouble(27));
                player.setBowlingEcon(res.getDouble(28));

                // player.setBest_match_bowling(res.getString(30).trim());
                player.setBowlingSr(res.getDouble(29));

                //player.setBowling_econ(res.getString(32).trim());
                player.setFourwick(res.getInt(30));

                player.setFivewick(res.getInt(31));
            }
            return player;

        } catch (Exception ex) {
            throw ex;
        }

    }

    @Override
    public Player getBatsmanById(int playerID) throws Exception {

        Player player = new Player();
        ResultSet res = null;
        try {
            CallableStatement cs;
            DBConnection ob = new DBConnection();
            Connection con = ob.createConnection();
            cs = con.prepareCall("{call SP_GetBatsmanByID(?)}");
            cs.setInt(1, playerID);
            res = cs.executeQuery();

            while (res.next()) {
                player.setPlayerID(res.getInt(1));
                player.setPlayerCode(res.getInt(2));
                player.setName(res.getString(3).trim());
                player.setPlayerRole(res.getString(4).trim());
                player.setNationality(res.getString(5).trim());

                player.setRecordType(res.getString(6).trim());
                player.setSoldValue(res.getInt(7));
                player.setBaseValue(res.getInt(8));
                player.setYear(res.getInt(9));
                player.setMatches(res.getInt(10));

                player.setBattedInnings(res.getInt(11));
                //player.setInnings_played(res.getString(10).trim());
                player.setNotOuts(res.getInt(12));
                player.setRunsScored(res.getInt(13));
                player.setHs(res.getInt(14));
                // player.setIs_star_batsman(res.getString(14).trim());
                player.setBattingAve(res.getDouble(15));

                player.setBf(res.getInt(16));
                player.setBattingSr(res.getDouble(17));
                player.setHundreds(res.getInt(18));
                player.setFifties(res.getInt(19));
                player.setFours(res.getInt(20));

                player.setSixes(res.getInt(21));

                //player.setCt(res.getString(22).trim());
                player.setCt(res.getInt(22));

                player.setStumpings(res.getInt(23));
                player.setBallsBowled(res.getInt(24));
                player.setRunsConceeded(res.getInt(25));

                //player.setIs_star_bowler(res.getString(27).trim());
                player.setWkts(res.getInt(26));
                player.setBowlingAve(res.getDouble(27));
                player.setBowlingEcon(res.getDouble(28));

                // player.setBest_match_bowling(res.getString(30).trim());
                player.setBowlingSr(res.getDouble(29));

                //player.setBowling_econ(res.getString(32).trim());
                player.setFourwick(res.getInt(30));

                player.setFivewick(res.getInt(31));
            }
            return player;

        } catch (Exception ex) {
            throw ex;
        }

    }

    @Override
    public Player getBowlerById(int playerID) throws Exception {

        Player player = new Player();
        ResultSet res = null;
        try {
            CallableStatement cs;
            DBConnection ob = new DBConnection();
            Connection con = ob.createConnection();
            cs = con.prepareCall("{call SP_GetBowlerByID(?)}");
            cs.setInt(1, playerID);
            res = cs.executeQuery();

            while (res.next()) {
                player.setPlayerID(res.getInt(1));
                player.setPlayerCode(res.getInt(2));
                player.setName(res.getString(3).trim());
                player.setPlayerRole(res.getString(4).trim());
                player.setNationality(res.getString(5).trim());

                player.setRecordType(res.getString(6).trim());
                player.setSoldValue(res.getInt(7));
                player.setBaseValue(res.getInt(8));
                player.setYear(res.getInt(9));
                player.setMatches(res.getInt(10));

                player.setBattedInnings(res.getInt(11));
                //player.setInnings_played(res.getString(10).trim());
                player.setNotOuts(res.getInt(12));
                player.setRunsScored(res.getInt(13));
                player.setHs(res.getInt(14));
                // player.setIs_star_batsman(res.getString(14).trim());
                player.setBattingAve(res.getDouble(15));

                player.setBf(res.getInt(16));
                player.setBattingSr(res.getDouble(17));
                player.setHundreds(res.getInt(18));
                player.setFifties(res.getInt(19));
                player.setFours(res.getInt(20));

                player.setSixes(res.getInt(21));

                //player.setCt(res.getString(22).trim());
                player.setCt(res.getInt(22));

                player.setStumpings(res.getInt(23));
                player.setBallsBowled(res.getInt(24));
                player.setRunsConceeded(res.getInt(25));

                //player.setIs_star_bowler(res.getString(27).trim());
                player.setWkts(res.getInt(26));
                player.setBowlingAve(res.getDouble(27));
                player.setBowlingEcon(res.getDouble(28));

                // player.setBest_match_bowling(res.getString(30).trim());
                player.setBowlingSr(res.getDouble(29));

                //player.setBowling_econ(res.getString(32).trim());
                player.setFourwick(res.getInt(30));

                player.setFivewick(res.getInt(31));

            }
            return player;

        } catch (Exception ex) {
            throw ex;
        }

    }

    @Override
    public Player getWicketKeeperById(int playerID) throws Exception {

        Player player = new Player();
        ResultSet res = null;
        try {
            CallableStatement cs;
            DBConnection ob = new DBConnection();
            Connection con = ob.createConnection();
            cs = con.prepareCall("{call SP_GetWicketKeeperByID(?)}");
            cs.setInt(1, playerID);
            res = cs.executeQuery();

            while (res.next()) {
                player.setPlayerID(res.getInt(1));
                player.setPlayerCode(res.getInt(2));
                player.setName(res.getString(3).trim());
                player.setPlayerRole(res.getString(4).trim());
                player.setNationality(res.getString(5).trim());

                player.setRecordType(res.getString(6).trim());
                player.setSoldValue(res.getInt(7));
                player.setBaseValue(res.getInt(8));
                player.setYear(res.getInt(9));
                player.setMatches(res.getInt(10));

                player.setBattedInnings(res.getInt(11));
                //player.setInnings_played(res.getString(10).trim());
                player.setNotOuts(res.getInt(12));
                player.setRunsScored(res.getInt(13));
                player.setHs(res.getInt(14));
                // player.setIs_star_batsman(res.getString(14).trim());
                player.setBattingAve(res.getDouble(15));

                player.setBf(res.getInt(16));
                player.setBattingSr(res.getDouble(17));
                player.setHundreds(res.getInt(18));
                player.setFifties(res.getInt(19));
                player.setFours(res.getInt(20));

                player.setSixes(res.getInt(21));

                //player.setCt(res.getString(22).trim());
                player.setCt(res.getInt(22));

                player.setStumpings(res.getInt(23));
                player.setBallsBowled(res.getInt(24));
                player.setRunsConceeded(res.getInt(25));

                //player.setIs_star_bowler(res.getString(27).trim());
                player.setWkts(res.getInt(26));
                player.setBowlingAve(res.getDouble(27));
                player.setBowlingEcon(res.getDouble(28));

                // player.setBest_match_bowling(res.getString(30).trim());
                player.setBowlingSr(res.getDouble(29));

                //player.setBowling_econ(res.getString(32).trim());
                player.setFourwick(res.getInt(30));

                player.setFivewick(res.getInt(31));
            }

            return player;

        } catch (Exception ex) {
            throw ex;
        }

    }

    @Override
    public int getHighestBattedInnings() {
        int highestBatsman = 0, highestBowler = 0, highestWicket = 0, highestAll = 0, highestBatedInnings = 0;
        ResultSet res = null;
        try {
            CallableStatement cs;
            DBConnection ob = new DBConnection();
            Connection con = ob.createConnection();
            cs = con.prepareCall("{call SP_GetMostNumberOfInningsPlayed}");

            res = cs.executeQuery();

            while (res.next()) {
                highestBatsman = (res.getInt(1));
                highestBowler = (res.getInt(2));
                highestWicket = (res.getInt(3));
                highestAll = (res.getInt(4));

            }
            highestAll = 1;
            highestBatedInnings = highestBatsman;
            for (int i = 0; i < 4; i++) {
                if (highestBatsman < highestBowler) {
                    highestBatedInnings = highestBowler;
                } else if (highestBatedInnings < highestWicket) {
                    highestBatedInnings = highestWicket;
                } else if (highestBatedInnings < highestAll) {
                    highestBatedInnings = highestAll;
                }
                return highestBatedInnings;
            }

        } catch (Exception e) {
        }
        return highestBatedInnings;

    }

    @Override
    public int getHighestBallsFaceed() {
        int highestBatsman = 0, highestBowler = 0, highestWicket = 0, highestAll = 0, highestNoBallsFaced = 0;
        ResultSet res = null;
        try {
            CallableStatement cs;
            DBConnection ob = new DBConnection();
            Connection con = ob.createConnection();
            cs = con.prepareCall("{call SP_GetMostNumberOfBallsFaced}");

            res = cs.executeQuery();

            while (res.next()) {
                highestBatsman = (res.getInt(1));
                highestBowler = (res.getInt(2));
                highestWicket = (res.getInt(3));
                highestAll = (res.getInt(4));

            }
            highestAll = 1;
            highestNoBallsFaced = highestBatsman;
            for (int i = 0; i < 4; i++) {
                if (highestBatsman < highestBowler) {
                    highestNoBallsFaced = highestBowler;
                } else if (highestNoBallsFaced < highestWicket) {
                    highestNoBallsFaced = highestWicket;
                } else if (highestNoBallsFaced < highestAll) {
                    highestNoBallsFaced = highestAll;
                }
                return highestNoBallsFaced;
            }

        } catch (Exception e) {
        }
        return highestNoBallsFaced;

    }

    @Override
    public int getHighestNumberOfBallsBowled() throws Exception {
        int highestBatsman = 0, highestBowler = 0, highestWicket = 0, highestAll = 0, highestNoBallsBowled = 0;
        ResultSet res = null;
        try {
            CallableStatement cs;
            DBConnection ob = new DBConnection();
            Connection con = ob.createConnection();
            cs = con.prepareCall("{call SP_GetMostNumberOfBallsBowled}");

            res = cs.executeQuery();

            while (res.next()) {
                highestBatsman = (res.getInt(1));
                highestWicket = (res.getInt(2));
                highestBowler = (res.getInt(3));
                highestAll = (res.getInt(4));

            }
            highestAll = 1;
            highestNoBallsBowled = highestBatsman;
            //System.err.println("THe highste number balls bowled :P   "+highestNoBallsBowled);
            for (int i = 0; i < 4; i++) {
                if (highestBatsman < highestBowler) {
                    highestNoBallsBowled = highestBowler;

                } else if (highestNoBallsBowled < highestWicket) {
                    highestNoBallsBowled = highestWicket;

                } else if (highestNoBallsBowled < highestAll) {
                    highestNoBallsBowled = highestAll;

                }
//                System.err.println("THe highste number balls bowled :P   "+highestNoBallsBowled);
                return highestNoBallsBowled;
            }

        } catch (Exception e) {
            System.err.println(e);
            throw e;

        }
        return highestNoBallsBowled;

    }

    @Override
    public List<PlayerListItem> getPlayerListItemCollectionBySpecialty(String playerType) throws Exception {

        List<PlayerListItem> players = new ArrayList<>();

        ResultSet res = null;
        try {
            CallableStatement cs;
            DBConnection ob = new DBConnection();
            Connection con = ob.createConnection();
            cs = con.prepareCall("{call SP_GetPlayerListByType(?)}");
            cs.setString(1, playerType);
            res = cs.executeQuery();

            while (res.next()) {
                PlayerListItem player = new PlayerListItem();
                player.setId(res.getString(1));
                player.setName(res.getString(2));
                players.add(player);
            }
            return players;

        } catch (Exception ex) {
            throw ex;
        }

    }

    @Override
    public String getPlayerTypeById(int playerID) throws Exception {
        String type = "";

        ResultSet res = null;
        try {
            CallableStatement cs;
            DBConnection ob = new DBConnection();
            Connection con = ob.createConnection();
            cs = con.prepareCall("{call SP_GetSpecialityFromMainTableById(?)}");
            cs.setInt(1, playerID);
            res = cs.executeQuery();

            while (res.next()) {
                type = res.getString(1);
            }
            return type;

        } catch (Exception ex) {
            throw ex;
        }

    }

    @Override
    public int getHighestNumberOfMatchesPlayed() throws Exception {
        int highestBatsman = 0, highestBowler = 0, highestWicket = 0, highestAll = 0, highestNoBallsBowled = 0;
        ResultSet res = null;
        try {
            CallableStatement cs;
            DBConnection ob = new DBConnection();
            Connection con = ob.createConnection();
            cs = con.prepareCall("{call SP_GetMostNumberOfMatchesPlayed}");

            res = cs.executeQuery();

            while (res.next()) {
                highestBatsman = (res.getInt(1));
                highestWicket = (res.getInt(2));
                highestBowler = (res.getInt(3));
                highestAll = (res.getInt(4));

            }
            highestAll = 1;
            highestNoBallsBowled = highestBatsman;
            //System.err.println("THe highste number balls bowled :P   "+highestNoBallsBowled);
            for (int i = 0; i < 4; i++) {
                if (highestBatsman < highestBowler) {
                    highestNoBallsBowled = highestBowler;

                } else if (highestNoBallsBowled < highestWicket) {
                    highestNoBallsBowled = highestWicket;

                } else if (highestNoBallsBowled < highestAll) {
                    highestNoBallsBowled = highestAll;

                }
//                System.err.println("THe highste number balls bowled :P   "+highestNoBallsBowled);
                return highestNoBallsBowled;
            }

        } catch (Exception e) {
            System.err.println(e);
            throw e;

        }
        return highestNoBallsBowled;

    }
//    get players

    @Override
    public List<Gens> getPlayers(long butget) {
        DBConnection ob = null;
        Connection con = null;
        Statement stmt = null;
        List<Gens> population = new ArrayList();
        try {
            ob = new DBConnection();
            con = ob.createConnection();
            stmt = con.createStatement();
            String query = "select * from player where SoldValue < " + butget;
            String query1 = "select count(*) as total from player where SoldValue < " + butget;
//            ResultSet rs = stmt.executeQuery(query);
            ResultSet rs = stmt.executeQuery(query1);
            int total = 0;
            if (rs.next()) {
                total = rs.getInt("total");
            }
            if (stmt != null) {
                stmt = null;
            }
            if (rs != null) {
                rs = null;
            }
            stmt = con.createStatement();
//            Gens[] population = new Gens[total];

            rs = stmt.executeQuery(query);
            while (rs.next()) {
                Gens gens = new Gens();
                gens.amount = rs.getLong("SoldValue");
                gens.name = rs.getString("Name");
                gens.role = rs.getString("PlayerRole");
                gens.batstrictRate = rs.getFloat("BattingSR");
                gens.ballstrictRate = rs.getFloat("BowlingSR");
                gens.ballavg = rs.getFloat("BowlingAve");
                gens.batavg = rs.getFloat("BattingAve");
                gens.id = rs.getLong("player_id2");
                population.add(gens);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                con = null;
            }
            if (stmt != null) {
                stmt = null;
            }
        }

//        System.out.println(population);
        return population;
    }

//    get maximum number in database according to the role
    @Override
    public float getMax(String role) {
        DBConnection ob = null;
        Connection con = null;
        Statement stmt = null;
        List<Gens> population = new ArrayList();
        int total = 0;
        try {
            ob = new DBConnection();
            con = ob.createConnection();
            stmt = con.createStatement();

            String query = "";
            ResultSet rs =null;
            switch (role) {
                case "Batsman":
                    query = "select max(BattingSR) as total from player where PlayerRole='Batsman'";
                     rs = stmt.executeQuery(query);

                    if (rs.next()) {
                        total = rs.getInt("total");
                    }
                    break;
                case "Bowler":
                    query = "select max(BowlingSR) as total from player where PlayerRole='Bowler'";
                     rs = stmt.executeQuery(query);

                    if (rs.next()) {
                        total = rs.getInt("total");
                    }
                    break;
                case "Wicketkeeper":
                    query = "select max(BattingSR) as total from player where PlayerRole='Wicketkeeper'";
                    rs = stmt.executeQuery(query);

                    if (rs.next()) {
                        total = rs.getInt("total");
                    }
                    break;
                case "Allrounder":
                   String query1 = "select max(BattingAve) as total from player where PlayerRole='Allrounder'";
                   query = "select max(BowlingAve) as total from player where PlayerRole='Allrounder'";
                    rs = stmt.executeQuery(query1);
                    int total1=0;
                    if (rs.next()) {
                        total1 = rs.getInt("total");
                    }
                    rs = stmt.executeQuery(query);

                    if (rs.next()) {
                        total = rs.getInt("total");
                    }
                    total = (total>total1)?total:total1;
                    
                    break;

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                con = null;
            }
            if (stmt != null) {
                stmt = null;
            }
        }

//        System.out.println(population);
        return total;
    }

}
