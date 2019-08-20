package framework.graphics.sprites;

public final class Animation {

    private Sprite[] sprites;
    private int speed;
    private int defaultSpeed;
    private int frameIndex;
    private long timer;
    private long lastUpdate;
    private boolean playing;
    private boolean finished;
    
    public Animation(SpriteSheet spriteSheet, int column, int row, int frames, int speed) {
    	this.sprites = new Sprite[frames];
        this.speed = speed;
        this.defaultSpeed = speed;
        this.frameIndex = 0;
        this.timer = 0;
        this.lastUpdate = 0;
        this.finished = false;
        
        // Add all sprites to the collection
        for (int i = 0; i < frames; i++) {
            sprites[i] = new Sprite(spriteSheet, column + i, row);
        }
        lastUpdate = System.currentTimeMillis();
        // Animation starts playing by default
        playing = true;
    }

    public void tick() {
        timer += System.currentTimeMillis() - lastUpdate;
        lastUpdate = System.currentTimeMillis();
        // If animation needs to be updated
        if (timer > speed) {
        	// Switch the sprite to the next frame
            frameIndex++;
            timer = 0;
            finished = false;
            // If there are no more frames left to be played
            if (frameIndex >= sprites.length) {
            	// Start the animation from the beginning
            	finished = true;
            	frameIndex = 0;
            }
        }
    }

    public Sprite getCurrentFrame() {
        return sprites[frameIndex];
    }
    
    public void resetSpeed() {
        speed = defaultSpeed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getFrameIndex() {
        return frameIndex;
    }

    public void setFrameIndex(int index) {
        frameIndex = index;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
    
    public boolean isFinished() {
    	return finished;
    }
}