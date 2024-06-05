package koks.event.impl;

import koks.event.Event;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 21:57
 */
public class AnimationEvent extends Event {

    private float[] leftLeg,rightLeg,body,leftArm,rightArm,head;
    private float[] leftLegPos,rightLegPos,bodyPos,leftArmPos,rightArmPos,headPos;

    public AnimationEvent(float[] leftLeg, float[] rightLeg, float[] body, float[] leftArm, float[] rightArm, float[] head, float[] leftLegPos, float[] rightLegPos, float[] bodyPos, float[] leftArmPos, float[] rightArmPos, float[] headPos) {
        this.leftLeg = leftLeg;
        this.rightLeg = rightLeg;
        this.body = body;
        this.leftArm = leftArm;
        this.rightArm = rightArm;
        this.head = head;
        this.leftLegPos = leftLegPos;
        this.rightLegPos = rightLegPos;
        this.bodyPos = bodyPos;
        this.leftArmPos = leftArmPos;
        this.rightArmPos = rightArmPos;
        this.headPos = headPos;
    }

    public void resetOffset() {
        this.bodyPos = new float[] {0,0,0};
        this.headPos = new float[] {0,0,0};
        this.leftArmPos = new float[] {0,0,0};
        this.leftLegPos = new float[] {0,0,0};
        this.rightArmPos = new float[] {0,0,0};
        this.rightLegPos = new float[] {0,0,0};
    }

    public void setBody(float x, float y, float z) {
        this.body = new float[] {x,y,z};
    }

    public void setHead(float x, float y, float z) {
        this.head = new float[] {x,y,z};
    }

    public void setLeftArm(float x, float y, float z) {
        this.leftArm = new float[] {x,y,z};
    }

    public void setLeftLeg(float x, float y, float z) {
        this.leftLeg = new float[] {x,y,z};
    }

    public void setRightArm(float x, float y, float z) {
        this.rightArm = new float[] {x,y,z};
    }

    public void setRightLeg(float x, float y, float z) {
        this.rightLeg = new float[] {x,y,z};
    }

    public void setLeftLegPos(float x, float y, float z) {
        this.leftLegPos = new float[] {x,y,z};
    }
    public void setRightLegPos(float x, float y, float z) {
        this.rightLegPos = new float[] {x,y,z};
    }
    public void setBodyPos(float x, float y, float z) {
        this.bodyPos = new float[] {x,y,z};
    }
    public void setLeftArmPos(float x, float y, float z) {
        this.leftArmPos = new float[] {x,y,z};
    }
    public void setRightArmPos(float x, float y, float z) {
        this.rightArmPos = new float[] {x,y,z};
    }
    public void setHeadPos(float x, float y, float z) {
        this.headPos = new float[] {x,y,z};
    }

    public float[] getLeftLeg() {
        return leftLeg;
    }

    public void setLeftLeg(float[] leftLeg) {
        this.leftLeg = leftLeg;
    }

    public float[] getRightLeg() {
        return rightLeg;
    }

    public void setRightLeg(float[] rightLeg) {
        this.rightLeg = rightLeg;
    }

    public float[] getBody() {
        return body;
    }

    public void setBody(float[] body) {
        this.body = body;
    }

    public float[] getLeftArm() {
        return leftArm;
    }

    public void setLeftArm(float[] leftArm) {
        this.leftArm = leftArm;
    }

    public float[] getRightArm() {
        return rightArm;
    }

    public void setRightArm(float[] rightArm) {
        this.rightArm = rightArm;
    }

    public float[] getHead() {
        return head;
    }

    public void setHead(float[] head) {
        this.head = head;
    }

    public float[] getLeftLegPos() {
        return leftLegPos;
    }

    public void setLeftLegPos(float[] leftLegPos) {
        this.leftLegPos = leftLegPos;
    }

    public float[] getRightLegPos() {
        return rightLegPos;
    }

    public void setRightLegPos(float[] rightLegPos) {
        this.rightLegPos = rightLegPos;
    }

    public float[] getBodyPos() {
        return bodyPos;
    }

    public void setBodyPos(float[] bodyPos) {
        this.bodyPos = bodyPos;
    }

    public float[] getLeftArmPos() {
        return leftArmPos;
    }

    public void setLeftArmPos(float[] leftArmPos) {
        this.leftArmPos = leftArmPos;
    }

    public float[] getRightArmPos() {
        return rightArmPos;
    }

    public void setRightArmPos(float[] rightArmPos) {
        this.rightArmPos = rightArmPos;
    }

    public float[] getHeadPos() {
        return headPos;
    }

    public void setHeadPos(float[] headPos) {
        this.headPos = headPos;
    }
}
