import Jama.Matrix;

public class JamaExample {
	
	double[][] array = {{1.,2.,3},{4.,5.,6.},{7.,8.,10.}};
	Matrix A = new Matrix(array);
	Matrix b = Matrix.random(3,1);
	Matrix x = A.solve(b);
	Matrix Residual = A.times(x).minus(b);
	double rnorm = Residual.normInf();

}
