package br.com.khadije.conversortemperatura.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class NoContentException extends AbstractThrowableProblem {
	private static final long serialVersionUID = 7133153272485206277L;

	public NoContentException() {
		super(null, "No content avaliable", Status.NO_CONTENT, "No content for this identification.");
	}
}
