package org.isma.pacman.resources;

import org.isma.slick2d.GameResourcesLoader;
import org.isma.slick2d.resources.SoundResources;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.openal.SoundStore;

import static java.lang.Float.parseFloat;

public class PacmanSoundResources extends SoundResources {

    private Sound dot;
    private Sound energizer;
    private Sound ghostEated;
    private Sound fruitEated;
    private Sound death;
    private Sound extraLife;

    private Music musicIntro;
    private Music musicSiren;
    private Music musicFrightened;
    private Music musicNaked;

    public PacmanSoundResources(GameResourcesLoader resourcesManager) {
        super(resourcesManager);
    }

    public void loadSounds() throws SlickException {

        dot = loadSound("sound.chomp.dot");
        energizer = loadSound("sound.chomp.energizer");
        ghostEated = loadSound("sound.ghost.eated");
        fruitEated = loadSound("sound.fruit.eated");
        death = loadSound("sound.pacman.death");
        extraLife = loadSound("sound.pacman.extralife");

        musicIntro = loadMusic("music.intro");
        musicSiren = loadMusic("music.siren");
        musicFrightened = loadMusic("music.frightened");
        musicNaked = loadMusic("music.naked");

        SoundStore.get().setMusicVolume(parseFloat(resourcesManager.get("music.volume")));
        SoundStore.get().setSoundVolume(parseFloat(resourcesManager.get("sound.volume")));
    }


    public void death() {
        musicPlayer.stop();
        death.play();
    }


    public void dot() {
        dot.play();
    }

    public void energizer() {
        energizer.play();
    }

    public long ghostEated() {
        ghostEated.play();
        return 1000l;
    }

    public void playMusicIntro() {
        musicPlayer.play(musicIntro);
    }

    public void playMusicSiren() {
        musicPlayer.loop(musicSiren);
    }

    public void playMusicFrightened() {
        musicPlayer.loop(musicFrightened);
    }

    public void playMusicNaked() {
        musicPlayer.loop(musicNaked);
    }

    public void fruitEated() {
        fruitEated.play();
    }

    public void extraLife() {
        extraLife.play();
    }
}
