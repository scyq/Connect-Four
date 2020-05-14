package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;



public class Datas {

    // record last chess position, for judge win or not
    // [0] is horizon [1] is vertical
    private int[] lastChess = {-1,-1};

    // save the chess information
    // -1-empty 0-red 1-yellow
    public int[][] chessInfo;

    // chess order
    // 0-red 1-yellow
    public static int chessOrder = 0;

    // for AI Mode
    // 0-person(red) 1-AI(yellow)
    public static int modelAIchessOrder = 0;


    // record  steps
    public static int steps = 0;

    public Datas() {
        // Initialize the chessBoard
        chessInfo = new int[Main.chessBoardHorizon][Main.chessBoardVertic];
        for (int i  = 0; i < Main.chessBoardHorizon; i++)
            for (int j = 0; j < Main.chessBoardVertic; j++)
                chessInfo[i][j] = -1;
    }

    // true-okay false-full
    public boolean updateChessInfo(int column) {
        if (Main.model == 1) {
            int last = chessOrder;
            for (int i = Main.chessBoardVertic-1; i >= 0; i--) {
                // prevent out of bound
                try {
                    if (chessInfo[column][i] == -1) {
                        chessInfo[column][i] = chessOrder;
                        chessOrder = Math.abs(chessOrder-1);
                        lastChess[0] = column;
                        lastChess[1] = i;
                    }
                }catch (Exception e) {
                    return false;
                }

                if (chessOrder != last) break;
            }

            // if chessOrder has not been changed, the line is full
            // therefore, return false
            if (chessOrder == last) return false;
            return true;
        }

        // AI Mode
        else {
            boolean AIflg = false;
            for (int i = Main.chessBoardVertic-1; i >= 0; i--) {
                // prevent out of bound
                try {
                    if (chessInfo[column][i] == -1) {
                        chessInfo[column][i] = modelAIchessOrder;
                        modelAIchessOrder = Math.abs(modelAIchessOrder-1);
                        lastChess[0] = column;
                        lastChess[1] = i;
                        AIflg = true;
                    }
                }catch (Exception e) {
                    return false;
                }

                if (AIflg) break;
            }

            if (!AIflg) return false;
            return true;

        }
    }


    // -1-no winner
    // 0-red win
    // 1-yellow win
    // 3-draw a tie
    // 13 conditions
    public int checkWin() {
        // because chessOder has changed, we have to switch
        int judgeColor;
        if (Main.model == 1)
            judgeColor = Math.abs(chessOrder-1);
        else
            judgeColor = Math.abs(modelAIchessOrder-1);
        int count = 1;

        // judge down
        if (Main.chessBoardVertic - lastChess[1] >= 4) {
            count = 1;
            for (int i = lastChess[1] + 1; i < Main.chessBoardVertic; i++) {
                if (chessInfo[lastChess[0]][i] == judgeColor)
                    count++;
                else break;
            }
            if (count >= 4) return judgeColor;
        }

        // judge left
        if (lastChess[0] >= 3) {
            count = 1;
            for (int i = lastChess[0] - 1; i >= 0; i--) {
                if (chessInfo[i][lastChess[1]] == judgeColor)
                    count++;
                else break;
            }
            if (count >= 4) return judgeColor;
        }

        // judge right
        if (Main.chessBoardHorizon - lastChess[0] >= 4) {
            count = 1;
            for (int i = lastChess[0] + 1; i < Main.chessBoardHorizon; i++) {
                if (chessInfo[i][lastChess[1]] == judgeColor)
                    count++;
                else break;
            }
            if (count >= 4) return judgeColor;
        }

        // judge left-down
        if (Main.chessBoardVertic - lastChess[1] >= 4 && lastChess[0] >= 3) {
            count = 1;
            for (int i = lastChess[0] - 1, j = lastChess[1] + 1; i >= 0 && j < Main.chessBoardVertic ; i--, j++) {
                if (chessInfo[i][j] == judgeColor)
                    count++;
                else break;
            }
            if (count >= 4) return judgeColor;
        }

        // judge right-down
        if (Main.chessBoardVertic - lastChess[1] >= 4 && Main.chessBoardHorizon - lastChess[0] >= 4) {
            for (int i = lastChess[0] + 1, j = lastChess[1] + 1; i < Main.chessBoardHorizon && j < Main.chessBoardVertic; i++, j++) {
                if (chessInfo[i][j] == judgeColor)
                    count++;
                else break;
            }
            if (count >= 4) return judgeColor;
        }

        // judge mid-0  1011
        if (lastChess[0] > 0 && lastChess[0] < Main.chessBoardHorizon - 2) {
            if (chessInfo[lastChess[0] - 1][lastChess[1]] == judgeColor)
                if (chessInfo[lastChess[0] + 1][lastChess[1]] == judgeColor && chessInfo[lastChess[0] + 2][lastChess[1]] == judgeColor)
                    return judgeColor;
        }

        // judge mid-1  1101
        if (lastChess[0] > 1 && lastChess[0] < Main.chessBoardHorizon - 1) {
            if (chessInfo[lastChess[0] + 1][lastChess[1]] == judgeColor)
                if (chessInfo[lastChess[0] - 1][lastChess[1]] == judgeColor && chessInfo[lastChess[0] - 2][lastChess[1]] == judgeColor)
                    return judgeColor;
        }

        // judge / mid-0 1011
        if (lastChess[0] > 0 && lastChess[0] < Main.chessBoardHorizon - 2 && lastChess[1] > 1 && Main.chessBoardVertic - lastChess[1] > 1) {
            if (chessInfo[lastChess[0] - 1][lastChess[1] + 1] == judgeColor)
                if (chessInfo[lastChess[0] + 1][lastChess[1] - 1] == judgeColor && chessInfo[lastChess[0] + 2][lastChess[1] - 2] == judgeColor)
                    return judgeColor;
        }

        // judge \ mid-0 1011
        if (lastChess[0] > 0 && lastChess[0] < Main.chessBoardHorizon - 2 && lastChess[1] > 1 && Main.chessBoardVertic - lastChess[1] > 2) {
            if (chessInfo[lastChess[0] - 1][lastChess[1] - 1] == judgeColor)
                if (chessInfo[lastChess[0] + 1][lastChess[1] + 1] == judgeColor && chessInfo[lastChess[0] + 2][lastChess[1] + 2] == judgeColor)
                    return judgeColor;
        }

        // judge / mid-1  1101
        if (lastChess[0] > 1 && lastChess[0] < Main.chessBoardHorizon - 1 && lastChess[1] > 0 && Main.chessBoardVertic - lastChess[1] > 2) {
            if (chessInfo[lastChess[0] + 1][lastChess[1] + 1] == judgeColor)
                if (chessInfo[lastChess[0] - 1][lastChess[1] + 1] == judgeColor && chessInfo[lastChess[0] - 2][lastChess[1] + 2] == judgeColor)
                    return judgeColor;
        }

        // judge \ mid-1  1101
        if (lastChess[0] > 1 && lastChess[0] < Main.chessBoardHorizon - 1 && lastChess[1] > 0 && Main.chessBoardVertic - lastChess[1] > 1) {
            if (chessInfo[lastChess[0] + 1][lastChess[1] + 1] == judgeColor)
                if (chessInfo[lastChess[0] - 1][lastChess[1] - 1] == judgeColor && chessInfo[lastChess[0] - 2][lastChess[1] - 2] == judgeColor)
                    return judgeColor;
        }

        // judge left-up
        if (lastChess[0] > 2 && lastChess[1] > 2) {
            if (chessInfo[lastChess[0] - 1][lastChess[1] - 1] == judgeColor)
                if (chessInfo[lastChess[0] - 2][lastChess[1] - 2] == judgeColor)
                    if (chessInfo[lastChess[0] - 3][lastChess[1] - 3] == judgeColor)
                        return judgeColor;
        }

        // judge right-up
        if (Main.chessBoardHorizon - lastChess[0] > 3 && lastChess[1] > 2) {
            if (chessInfo[lastChess[0] + 1][lastChess[1] - 1] == judgeColor)
                if (chessInfo[lastChess[0] + 2][lastChess[1] - 2] == judgeColor)
                    if (chessInfo[lastChess[0] + 3][lastChess[1] - 3] == judgeColor)
                        return judgeColor;
        }

        // draw a tie
        if (steps >= Main.chessBoardHorizon * Main.chessBoardVertic)
            return 3;


        return -1;
    }

    // return random position
    public int easyAI() {
        int min = 0;
        int max = Main.chessBoardHorizon - 1;
        Random rd = new Random();
        int pos = rd.nextInt(max)%(max - min + 1) + min;

        return pos;
    }

    // only for hard AI to check
    private int AICheck(int[][] aiInfo, int chessPlace[], int jc) {
        int judgeColor = jc;
        int count = 1;

        // judge down
        if (Main.chessBoardVertic - chessPlace[1] >= 4) {
            count = 1;
            for (int i = chessPlace[1] + 1; i < Main.chessBoardVertic; i++) {
                if (aiInfo[chessPlace[0]][i] == judgeColor)
                    count++;
                else break;
            }
            if (count >= 4) return judgeColor;
        }

        // judge left
        if (chessPlace[0] >= 3) {
            count = 1;
            for (int i = chessPlace[0] - 1; i >= 0; i--) {
                if (aiInfo[i][chessPlace[1]] == judgeColor)
                    count++;
                else break;
            }
            if (count >= 4) return judgeColor;
        }

        // judge right
        if (Main.chessBoardHorizon - chessPlace[0] >= 4) {
            count = 1;
            for (int i = chessPlace[0] + 1; i < Main.chessBoardHorizon; i++) {
                if (aiInfo[i][chessPlace[1]] == judgeColor)
                    count++;
                else break;
            }
            if (count >= 4) return judgeColor;
        }

        // judge left-down
        if (Main.chessBoardVertic - chessPlace[1] >= 4 && chessPlace[0] >= 3) {
            count = 1;
            for (int i = chessPlace[0] - 1, j = chessPlace[1] + 1; i >= 0 && j < Main.chessBoardVertic ; i--, j++) {
                if (aiInfo[i][j] == judgeColor)
                    count++;
                else break;
            }
            if (count >= 4) return judgeColor;
        }

        // judge right-down
        if (Main.chessBoardVertic - chessPlace[1] >= 4 && Main.chessBoardHorizon - chessPlace[0] >= 4) {
            for (int i = chessPlace[0] + 1, j = chessPlace[1] + 1; i < Main.chessBoardHorizon && j < Main.chessBoardVertic; i++, j++) {
                if (aiInfo[i][j] == judgeColor)
                    count++;
                else break;
            }
            if (count >= 4) return judgeColor;
        }

        // judge mid-0  1011
        if (chessPlace[0] > 0 && chessPlace[0] < Main.chessBoardHorizon - 2) {
            if (aiInfo[chessPlace[0] - 1][chessPlace[1]] == judgeColor)
                if (aiInfo[chessPlace[0] + 1][chessPlace[1]] == judgeColor && aiInfo[chessPlace[0] + 2][chessPlace[1]] == judgeColor)
                    return judgeColor;
        }

        // judge mid-1  1101
        if (chessPlace[0] > 1 && chessPlace[0] < Main.chessBoardHorizon - 1) {
            if (aiInfo[chessPlace[0] + 1][chessPlace[1]] == judgeColor)
                if (aiInfo[chessPlace[0] - 1][chessPlace[1]] == judgeColor && aiInfo[chessPlace[0] - 2][chessPlace[1]] == judgeColor)
                    return judgeColor;
        }

        // judge / mid-0 1011
        if (chessPlace[0] > 0 && chessPlace[0] < Main.chessBoardHorizon - 2 && chessPlace[1] > 1 && Main.chessBoardVertic - chessPlace[1] > 1) {
            if (aiInfo[chessPlace[0] - 1][chessPlace[1] + 1] == judgeColor)
                if (aiInfo[chessPlace[0] + 1][chessPlace[1] - 1] == judgeColor && aiInfo[chessPlace[0] + 2][chessPlace[1] - 2] == judgeColor)
                    return judgeColor;
        }

        // judge \ mid-0 1011
        if (chessPlace[0] > 0 && chessPlace[0] < Main.chessBoardHorizon - 2 && chessPlace[1] > 1 && Main.chessBoardVertic - chessPlace[1] > 2) {
            if (aiInfo[chessPlace[0] - 1][chessPlace[1] - 1] == judgeColor)
                if (aiInfo[chessPlace[0] + 1][chessPlace[1] + 1] == judgeColor && aiInfo[chessPlace[0] + 2][chessPlace[1] + 2] == judgeColor)
                    return judgeColor;
        }

        // judge / mid-1  1101
        if (chessPlace[0] > 1 && chessPlace[0] < Main.chessBoardHorizon - 1 && chessPlace[1] > 0 && Main.chessBoardVertic - chessPlace[1] > 2) {
            if (aiInfo[chessPlace[0] + 1][chessPlace[1] + 1] == judgeColor)
                if (aiInfo[chessPlace[0] - 1][chessPlace[1] + 1] == judgeColor && aiInfo[chessPlace[0] - 2][chessPlace[1] + 2] == judgeColor)
                    return judgeColor;
        }

        // judge \ mid-1  1101
        if (chessPlace[0] > 1 && chessPlace[0] < Main.chessBoardHorizon - 1 && chessPlace[1] > 0 && Main.chessBoardVertic - chessPlace[1] > 1) {
            if (aiInfo[chessPlace[0] + 1][chessPlace[1] + 1] == judgeColor)
                if (aiInfo[chessPlace[0] - 1][chessPlace[1] - 1] == judgeColor && aiInfo[chessPlace[0] - 2][chessPlace[1] - 2] == judgeColor)
                    return judgeColor;
        }

        // judge left-up
        if (chessPlace[0] > 2 && chessPlace[1] > 2) {
            if (aiInfo[chessPlace[0] - 1][chessPlace[1] - 1] == judgeColor)
                if (aiInfo[chessPlace[0] - 2][chessPlace[1] - 2] == judgeColor)
                    if (aiInfo[chessPlace[0] - 3][chessPlace[1] - 3] == judgeColor)
                        return judgeColor;
        }

        // judge right-up
        if (Main.chessBoardHorizon - chessPlace[0] > 3 && chessPlace[1] > 2) {
            if (aiInfo[chessPlace[0] + 1][chessPlace[1] - 1] == judgeColor)
                if (aiInfo[chessPlace[0] + 2][chessPlace[1] - 2] == judgeColor)
                    if (aiInfo[chessPlace[0] + 3][chessPlace[1] - 3] == judgeColor)
                        return judgeColor;
        }

        // draw a tie
        if (steps + 1 >= Main.chessBoardHorizon * Main.chessBoardVertic)
            return 3;

        return -1;
    }

    public int hardAI() {

        // copy the chess situation
        int[][] copyChessInfo = new int[Main.chessBoardHorizon][Main.chessBoardVertic];

        // record the value
        // 10 - have to
        int ans[] = new int [Main.chessBoardHorizon];
        for (int i = 0; i < Main.chessBoardHorizon; i++)
            ans[i] = 0;


        // if win
        for (int k = 0; k < Main.chessBoardHorizon; k++) {
            // refresh
            int[] lastCP = {-1,-1};

            // refresh
            for (int i = 0; i < Main.chessBoardVertic; i++)
                for (int j = 0; j < Main.chessBoardHorizon; j++)
                    copyChessInfo[j][i] = chessInfo[j][i];

            // chess
            try {
                for(int j = Main.chessBoardVertic - 1; j >= 0; j--) {

                    if (copyChessInfo[k][j] == -1) {
                        copyChessInfo[k][j] = 1;	// AI put here
                        lastCP[0] = k;
                        lastCP[1] = j;

                        if (AICheck(copyChessInfo, lastCP, 1) == 1)
                            ans[k] += 13;
                        break;
                    }

                }

            } catch (Exception e) {
                continue;
            }

        }

        // if there have to put prevent win
        // for which line that opponent put
        // k is next pawn the opponent put
        for (int k = 0; k < Main.chessBoardHorizon; k++) {

            // refresh
            int[] lastCP = {-1,-1};

            // refresh
            for (int i = 0; i < Main.chessBoardVertic; i++)
                for (int j = 0; j < Main.chessBoardHorizon; j++)
                    copyChessInfo[j][i] = chessInfo[j][i];

            // chess
            try {
                for(int j = Main.chessBoardVertic - 1; j >= 0; j--) {

                    if (copyChessInfo[k][j] == -1) {
                        copyChessInfo[k][j] = 0;	// person put here
                        lastCP[0] = k;
                        lastCP[1] = j;

                        if (AICheck(copyChessInfo, lastCP, 0) == 0) {
                            ans[k] += 15;
                        }

                        break;
                    }

                }

            } catch (Exception e) {
                continue;
            }

        }

        // refresh
        for (int i = 0; i < Main.chessBoardVertic; i++)
            for (int j = 0; j < Main.chessBoardHorizon; j++)
                copyChessInfo[j][i] = chessInfo[j][i];

        // judge free connect to *00*
        for (int k = Main.chessBoardVertic - 1; k >=0; k--) {
            // first row
            if (k == Main.chessBoardVertic - 1) {
                for (int o = 1; o < Main.chessBoardHorizon; o++) {
                    if (copyChessInfo[o][k] == 0 && o < Main.chessBoardHorizon - 2)
                        if (copyChessInfo[o+1][k] == 0) {
                            if (copyChessInfo[o-1][k] == -1)
                                ans[--o] += 9;
                            if (copyChessInfo[o+2][k] == -1)
                                ans[o+2] += 9;
                            break;
                        }
                }

            }

            else {
                for (int o = 1; o < Main.chessBoardHorizon; o++) {
                    if (copyChessInfo[o][k] == 0 && o < Main.chessBoardHorizon - 2)
                        if (copyChessInfo[o+1][k] == 0)
                            if (copyChessInfo[o+2][k+1] != -1 && copyChessInfo[o-1][k+1] != -1) {
                                if (copyChessInfo[o-1][k] == -1)
                                    ans[--o] += 9;
                                if (copyChessInfo[o+2][k] == -1)
                                    ans[o+2] += 9;
                                break;
                            }

                }
            }


        }

        // judge help opponent
        for (int k = 0; k < Main.chessBoardHorizon; k++) {
            // refresh
            int[] lastCP = {-1,-1};

            // refresh
            for (int i = 0; i < Main.chessBoardVertic; i++)
                for (int j = 0; j < Main.chessBoardHorizon; j++)
                    copyChessInfo[j][i] = chessInfo[j][i];

            try {
                for(int z = Main.chessBoardVertic - 1; z >= 0; z--) {
                    boolean x = false;
                    if (copyChessInfo[k][z] == -1) {
                        copyChessInfo[k][z] = 1;
                        lastCP[0] = k;
                        lastCP[1] = z;
                        x = true;

                        copyChessInfo[lastCP[0]][lastCP[1] - 1] = 0;
                        lastCP[1] --;

                        if (AICheck(copyChessInfo, lastCP, 0) == 0) {
                            ans[k] -= 11;
                        }

                    }
                    if (x) break;
                }

            } catch (Exception e) {

            }
        }

        // give judge
        // for each line
        for (int k = 0; k < Main.chessBoardHorizon; k++) {
            // refresh
            int[] lastCP = {-1,-1};

            // refresh
            for (int i = 0; i < Main.chessBoardVertic; i++)
                for (int j = 0; j < Main.chessBoardHorizon; j++)
                    copyChessInfo[j][i] = chessInfo[j][i];

            // chess
            try {
                for(int j = Main.chessBoardVertic - 1; j >= 0; j--) {

                    if (copyChessInfo[k][j] == -1) {
                        copyChessInfo[k][j] = 1;	// AI put here
                        lastCP[0] = k;
                        lastCP[1] = j;


                        // if win then win
                        if (AICheck(copyChessInfo, lastCP, 1) == 1)
                            ans[k] += 11;

                        // judge three
                        // judge down
                        if (Main.chessBoardVertic - lastCP[1] >= 3) {
                            int count = 1;
                            for (int i = lastCP[1] + 1; i < Main.chessBoardVertic; i++) {
                                if (copyChessInfo[lastCP[0]][i] == 1)
                                    count++;
                                else break;
                            }
                            if (count >= 3) ans[k] += 2;
                        }

                        // judge left
                        if (lastCP[0] >= 2) {
                            int count = 1;
                            for (int i = lastCP[0] - 1; i >= 0; i--) {
                                if (copyChessInfo[i][lastCP[1]] == 1)
                                    count++;
                                else break;
                            }
                            if (count >= 3) ans[k] += 2;
                        }

                        // judge right
                        if (Main.chessBoardHorizon - lastCP[0] >= 3) {
                            int count = 1;
                            for (int i = lastCP[0] + 1; i < Main.chessBoardHorizon; i++) {
                                if (copyChessInfo[i][lastCP[1]] == 1)
                                    count++;
                                else break;
                            }
                            if (count >= 3) ans[k] += 2;
                        }

                        // judge left-down
                        if (Main.chessBoardVertic - lastCP[1] >= 3 && lastCP[0] >= 2) {
                            int count = 1;
                            for (int i = lastCP[0] - 1, z = lastCP[1] + 1; i >= 0 && z < Main.chessBoardVertic ; i--, z++) {
                                if (copyChessInfo[i][z] == 1)
                                    count++;
                                else break;
                            }
                            if (count >= 3) ans[k] += 2;
                        }

                        // judge right-down
                        if (Main.chessBoardVertic - lastCP[1] >= 3 && Main.chessBoardHorizon - lastCP[0] >= 3) {
                            int count = 1;
                            for (int i = lastCP[0] + 1, z = lastCP[1] + 1; i < Main.chessBoardHorizon && z < Main.chessBoardVertic; i++, z++) {
                                if (copyChessInfo[i][z] == 1)
                                    count++;
                                else break;
                            }
                            if (count >= 3) ans[k] += 2;
                        }

                        // judge mid-0  101
                        if (lastCP[0] > 0 && lastCP[0] < Main.chessBoardHorizon - 1) {
                            if (copyChessInfo[lastCP[0] - 1][lastCP[1]] == 1)
                                if (copyChessInfo[lastCP[0] + 1][lastCP[1]] == 1)
                                    ans[k] += 2;
                        }

                        // judge mid-1  101
                        if (lastCP[0] > 0 && lastCP[0] < Main.chessBoardHorizon - 1) {
                            if (copyChessInfo[lastCP[0] + 1][lastCP[1]] == 1)
                                if (copyChessInfo[lastCP[0] - 1][lastCP[1]] == 1)
                                    ans[k] += 2;
                        }

                        // judge left-up
                        if (lastCP[0] > 1 && lastCP[1] > 1) {
                            if (copyChessInfo[lastCP[0] - 1][lastCP[1] - 1] == 1)
                                if (copyChessInfo[lastCP[0] - 2][lastCP[1] - 2] == 1)
                                    ans[k] += 2;
                        }

                        // judge right-up
                        if (Main.chessBoardHorizon - lastCP[0] > 2 && lastCP[1] > 1) {
                            if (copyChessInfo[lastCP[0] + 1][lastCP[1] - 1] == 1)
                                if (copyChessInfo[lastCP[0] + 2][lastCP[1] - 2] == 1)
                                    ans[k] += 2;
                        }

                        break;
                    }

                }

            } catch (Exception e) {
                continue;
            }


            // chess
            try {
                for(int j = Main.chessBoardVertic - 1; j >= 0; j--) {

                    boolean x = false;
                    if (copyChessInfo[k][j] == -1) {
                        copyChessInfo[k][j] = 1;	// AI put here
                        lastCP[0] = k;
                        lastCP[1] = j;
                        x = true;

                        // judge two
                        // judge down
                        if (Main.chessBoardVertic - lastCP[1] >= 2) {
                            int count = 1;
                            for (int i = lastCP[1] + 1; i < Main.chessBoardVertic; i++) {
                                if (copyChessInfo[lastCP[0]][i] == 1)
                                    count++;
                                else break;
                            }
                            if (count >= 2) ans[k] += 1;
                        }

                        // judge left
                        if (lastCP[0] >= 1) {
                            int count = 1;
                            for (int i = lastCP[0] - 1; i >= 0; i--) {
                                if (copyChessInfo[i][lastCP[1]] == 1)
                                    count++;
                                else break;
                            }
                            if (count >= 3) ans[k] += 3;
                        }

                        // judge right
                        if (Main.chessBoardHorizon - lastCP[0] >= 2) {
                            int count = 1;
                            for (int i = lastCP[0] + 1; i < Main.chessBoardHorizon; i++) {
                                if (copyChessInfo[i][lastCP[1]] == 1)
                                    count++;
                                else break;
                            }
                            if (count >= 2) ans[k] += 1;
                        }

                        // judge left-down
                        if (Main.chessBoardVertic - lastCP[1] >= 2 && lastCP[0] >= 1) {
                            int count = 1;
                            for (int i = lastCP[0] - 1, z = lastCP[1] + 1; i >= 0 && z < Main.chessBoardVertic ; i--, z++) {
                                if (copyChessInfo[i][z] == 1)
                                    count++;
                                else break;
                            }
                            if (count >= 2) ans[k] += 1;
                        }

                        // judge right-down
                        if (Main.chessBoardVertic - lastCP[1] >= 2 && Main.chessBoardHorizon - lastCP[0] >= 1) {
                            int count = 1;
                            for (int i = lastCP[0] + 1, z = lastCP[1] + 1; i < Main.chessBoardHorizon && z < Main.chessBoardVertic; i++, z++) {
                                if (copyChessInfo[i][z] == 1)
                                    count++;
                                else break;
                            }
                            if (count >= 2) ans[k] += 1;
                        }

                        // judge mid-0  101
                        if (lastCP[0] > 0 && lastCP[0] < Main.chessBoardHorizon - 1) {
                            if (copyChessInfo[lastCP[0] - 1][lastCP[1]] == 1)
                                if (copyChessInfo[lastCP[0] + 1][lastCP[1]] == 1)
                                    ans[k] += 1;
                        }

                        // judge mid-1  101
                        if (lastCP[0] > 1 && lastCP[0] < Main.chessBoardHorizon - 1) {
                            if (copyChessInfo[lastCP[0] + 1][lastCP[1]] == 1)
                                if (copyChessInfo[lastCP[0] - 1][lastCP[1]] == 1)
                                    ans[k] += 1;
                        }

                        // judge / mid-0 101
                        if (lastCP[0] > 0 && lastCP[0] < Main.chessBoardHorizon - 1 && lastCP[1] > 1 && Main.chessBoardVertic - lastCP[1] > 0) {
                            if (copyChessInfo[lastCP[0] - 1][lastCP[1] + 1] == 1)
                                if (copyChessInfo[lastCP[0] + 1][lastCP[1] - 1] == 1)
                                    ans[k] += 1;
                        }

                        // judge \ mid-0 101
                        if (lastCP[0] > 0 && lastCP[0] < Main.chessBoardHorizon - 1 && lastCP[1] > 1 && Main.chessBoardVertic - lastCP[1] > 1) {
                            if (copyChessInfo[lastCP[0] - 1][lastCP[1] - 1] == 1)
                                if (copyChessInfo[lastCP[0] + 1][lastCP[1] + 1] == 1)
                                    ans[k] += 1;
                        }

                        // judge / mid-1  101
                        if (lastCP[0] > 0 && lastCP[0] < Main.chessBoardHorizon - 1 && lastCP[1] > 0 && Main.chessBoardVertic - lastCP[1] > 1) {
                            if (copyChessInfo[lastCP[0] + 1][lastCP[1] + 1] == 1)
                                if (copyChessInfo[lastCP[0] - 1][lastCP[1] + 1] == 1)
                                    ans[k] += 1;
                        }

                        // judge \ mid-1  101
                        if (lastCP[0] > 0 && lastCP[0] < Main.chessBoardHorizon - 1 && lastCP[1] > 0 && Main.chessBoardVertic - lastCP[1] > 0) {
                            if (copyChessInfo[lastCP[0] + 1][lastCP[1] + 1] == 1)
                                if (copyChessInfo[lastCP[0] - 1][lastCP[1] - 1] == 1)
                                    ans[k] += 1;
                        }

                        // judge left-up
                        if (lastCP[0] > 1 && lastCP[1] > 1) {
                            if (copyChessInfo[lastCP[0] - 1][lastCP[1] - 1] == 1)
                                if (copyChessInfo[lastCP[0] - 2][lastCP[1] - 2] == 1)
                                    ans[k] += 1;
                        }

                        // judge right-up
                        if (Main.chessBoardHorizon - lastCP[0] > 2 && lastCP[1] > 1) {
                            if (copyChessInfo[lastCP[0] + 1][lastCP[1] - 1] == 1)
                                if (copyChessInfo[lastCP[0] + 2][lastCP[1] - 2] == 1)
                                    ans[k] += 1;
                        }


                    }
                    if (x) break;
                }

            } catch (Exception e) {
                continue;
            }

        }

        int num = 0;
        // find the max
        for (int i : ans) System.out.print(i + " ");
        System.out.println("");
        while(num > -Main.chessBoardHorizon) {
            int maxAns = Arrays.stream(ans).max().getAsInt();
            int pos = -1;
            for (int i = 0; i < Main.chessBoardHorizon; i++)
                if (ans[i] == maxAns)
                    pos = i;
            for (int i = Main.chessBoardVertic - 1; i >=0; i--)
                // has empty
                if (chessInfo[pos][i] == -1)
                    return pos;
            ans[pos] = --num;
        }

        return 0;
    }



}
