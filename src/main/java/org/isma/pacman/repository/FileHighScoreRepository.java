package org.isma.pacman.repository;

import org.isma.pacman.entity.RankedPlayer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileHighScoreRepository extends HighScoreRepository {
    private static final String FILENAME = "scores.data";
    private static final String SEPARATOR = ":";

    private File file;


    public FileHighScoreRepository() throws Exception {
    }

    protected void load() throws Exception {
        file = new File(FILENAME);
        if (!file.exists()) {
            return;
        }
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.split(SEPARATOR);
            String name = split[0];
            long score = Long.parseLong(split[1]);
            getRankedPlayers().add(new RankedPlayer(name, score));
        }
    }

    @Override
    public void save() throws Exception {
        String carriageReturn = System.getProperty("line.separator");

        StringBuilder sb = new StringBuilder();
        for (RankedPlayer rankedPlayer : getRankedPlayers()) {
            String line = rankedPlayer.name + SEPARATOR + rankedPlayer.score;
            if (sb.length() > 0) {
                sb.append(carriageReturn);
            }
            sb.append(line);
        }
        writeToFile(sb.toString());

    }

    private void writeToFile(String text) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
        bw.write(text);
        bw.close();
    }


}
