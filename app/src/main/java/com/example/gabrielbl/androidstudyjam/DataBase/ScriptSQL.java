package com.example.gabrielbl.androidstudyjam.DataBase;

/**
 * Created by gabrielbl on 26/04/16.
 */
public class ScriptSQL {

    public static String getCreateFilme() {

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS FILMES ( ");
        sqlBuilder.append("_id\t\t\tINTEGER\t\tNOT NULL ");
        sqlBuilder.append("\t\t\t\t\tPRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("titulo\t\t\tVARCHAR(128), ");
        sqlBuilder.append("descricao\t\tVARCHAR(1024), ");
        sqlBuilder.append("elenco\t\t\tVARCHAR(128), ");
        sqlBuilder.append("direcao\t\t\tVARCHAR(128), ");
        sqlBuilder.append("duracao\t\t\tVARCHAR(7), ");
        sqlBuilder.append("genero\t\t\tVARCHAR(17), ");
        sqlBuilder.append("classificacao\t\tVARCHAR(8), ");
        sqlBuilder.append("dataLancamento\t\tVARCHAR(10), ");
        sqlBuilder.append("imgUrl\t\t\tVARCHAR(66), ");
        sqlBuilder.append("trailerUrl\t\tVARCHAR(40), ");
        sqlBuilder.append("filmeUrl\t\tVARCHAR(66) ");
        sqlBuilder.append("); ");

        return sqlBuilder.toString();
    }

}
