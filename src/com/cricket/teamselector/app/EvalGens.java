/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cricket.teamselector.app;

/**
 *
 * @author charli
 */
public class EvalGens {

    public EvalGens(float ballFit, float batFit, float keepFit, float allroundFit) {
        this.ballFit = ballFit;
        this.batFit = batFit;
        this.keepFit = keepFit;
        this.allroundFit = allroundFit;
    }

    private float ballFit;
    private float batFit;
    private float keepFit;
    private float allroundFit;
    

    public float getBallFit() {
        return ballFit;
    }

    public void setBallFit(float ballFit) {
        this.ballFit = ballFit;
    }

    public float getBatFit() {
        return batFit;
    }

    public void setBatFit(float batFit) {
        this.batFit = batFit;
    }

    public float getKeepFit() {
        return keepFit;
    }

    public void setKeepFit(float keepFit) {
        this.keepFit = keepFit;
    }

    public float getAllroundFit() {
        return allroundFit;
    }

    public void setAllroundFit(float allroundFit) {
        this.allroundFit = allroundFit;
    }

}
