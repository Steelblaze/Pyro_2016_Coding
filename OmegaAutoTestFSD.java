/* Copyright (c) 2014 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
public class OmegaAutoTestFSD extends OpMode {

	DcMotor motorFrontRight;
	DcMotor motorBackRight;
	DcMotor motorFrontLeft;
	DcMotor motorBackLeft;

	Servo climbers;

	public void forward(){
		motorFrontRight.setPower(1);
		motorFrontLeft.setPower(1);
		motorBackRight.setPower(1);
		motorBackLeft.setPower(1);
	}
	public void halt(){
		motorFrontRight.setPower(0);
		motorFrontLeft.setPower(0);
		motorBackRight.setPower(0);
		motorBackLeft.setPower(0);
	}
	public void reset(){
		motorFrontLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
		motorFrontRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
	}
	public void revert()
	{
		motorFrontLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
		motorFrontRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
	}

	public OmegaAutoTestFSD() {

	}
	public void drop(){
		climbers.setPosition(1);
	}

	@Override
	public void init() {

		motorFrontLeft = hardwareMap.dcMotor.get("motor_1");
		motorFrontRight = hardwareMap.dcMotor.get("motor_2");
		motorBackLeft = hardwareMap.dcMotor.get("motor_3");
		motorBackRight = hardwareMap.dcMotor.get("motor_4");
		climbers = hardwareMap.servo.get("servo_1");
		motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);
		motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
		motorFrontLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
		motorFrontRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
		motorBackLeft.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
		motorBackRight.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
	}


	@Override
	public void loop() {

		if(motorFrontLeft.getCurrentPosition()<= -1020 || motorFrontRight.getCurrentPosition()>=1020) {
			halt();
			drop();
		}
		else{
			forward();
		}
		reset();
		revert();
		telemetry.addData("Encoder", motorFrontLeft.getCurrentPosition());
	}


	@Override
	public void stop() {
	reset();
	}
}
