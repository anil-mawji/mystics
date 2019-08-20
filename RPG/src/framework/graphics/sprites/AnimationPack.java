package framework.graphics.sprites;

public class AnimationPack {
	
	private Animation[] animations;
	
	public AnimationPack(SpriteSheet spriteSheet, int speed, int... properties) {
		this.animations = new Animation[properties.length / 3];
		
		int index = 0;
		for (int i = 0; i < properties.length; i += 3) {
			animations[index] = new Animation(
					spriteSheet,
					properties[i],     // Column
					properties[i + 1], // Row
					properties[i + 2], // Frames
					speed
			);
			index++;
		}
	}
	
	public Animation get(int index) {
		return animations[index];
	}
}