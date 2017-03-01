package demo;

class TrainingBoards {
	
	public static Square[][] getTrainingBoard(int board){
		switch(board){
			case 0:
				return makeEmptyBoard();
		/*	case 1:
				return makeBoard1();
			case 2:
				return makeBoard2();*/
			case 3:
				return makeBoard3();
			case 4:
				return makeBoard4();
			/*case 5:
				return makeBoard5();
			case 6:
				return makeBoard6();*/
			case 7:
				return makeBoard7();
			case 8:
				return makeBoard8();
		/*	case 9:
				return makeBoard9();
			case 10:
				return makeBoard10();*/
			case 11:
				return makeBoard11();
			case 12:
				return makeBoard12();
		/*	case 13:
				return makeBoard13();
			case 14:
				return makeBoard14();*/
			default:
				return makeEmptyBoard();	
		}
	}
	
	//X is always the first player
	public static Square[][] makeEmptyBoard(){
		return new Square[][]{{Square.EMPTY,Square.EMPTY,Square.EMPTY},
							  {Square.EMPTY,Square.EMPTY,Square.EMPTY},
					          {Square.EMPTY,Square.EMPTY,Square.EMPTY}};
	}
/*	//1 MOVE
	public static Square[][] makeBoard1(){
		return new Square[][]{{Square.X,	Square.EMPTY,Square.EMPTY},
							  {Square.EMPTY,Square.EMPTY,Square.EMPTY},
					          {Square.EMPTY,Square.EMPTY,Square.EMPTY}};
	}
	
	public static Square[][] makeBoard2(){
		return new Square[][]{{Square.EMPTY,Square.X,	 Square.EMPTY},
							  {Square.EMPTY,Square.EMPTY,Square.EMPTY},
					          {Square.EMPTY,Square.EMPTY,Square.EMPTY}};
	}*/
	//2 MOVES
	public static Square[][] makeBoard3(){
		return new Square[][]{{Square.EMPTY,Square.EMPTY,Square.O},
							  {Square.EMPTY,Square.X,	 Square.EMPTY},
					          {Square.EMPTY,Square.EMPTY,Square.EMPTY}};
	}
	
	public static Square[][] makeBoard4(){
		return new Square[][]{{Square.EMPTY,Square.O,	 Square.EMPTY},
							  {Square.X,	Square.EMPTY,Square.EMPTY},
					          {Square.EMPTY,Square.EMPTY,Square.EMPTY}};
	}
	/*//3 MOVES
	public static Square[][] makeBoard5(){
		return new Square[][]{{Square.X,	Square.X,	 Square.EMPTY},
							  {Square.EMPTY,Square.O,	 Square.EMPTY},
					          {Square.EMPTY,Square.EMPTY,Square.EMPTY}};
	}
	
	public static Square[][] makeBoard6(){
		return new Square[][]{{Square.EMPTY,Square.EMPTY,Square.EMPTY},
							  {Square.X,	Square.O,	 Square.EMPTY},
					          {Square.EMPTY,Square.X,	 Square.EMPTY}};
	}*/
	//4 MOVES
	public static Square[][] makeBoard7(){
		return new Square[][]{{Square.X,	Square.X,	 Square.O},
							  {Square.EMPTY,Square.EMPTY,Square.EMPTY},
					          {Square.EMPTY,Square.EMPTY,Square.O}};
	}
	
	public static Square[][] makeBoard8(){
		return new Square[][]{{Square.O,	Square.EMPTY,Square.EMPTY},
							  {Square.EMPTY,Square.O,	 Square.EMPTY},
					          {Square.X,	Square.EMPTY,Square.X}};
	}
	/*//5 MOVES
	public static Square[][] makeBoard9(){
		return new Square[][]{{Square.X,	Square.EMPTY,Square.EMPTY},
							  {Square.EMPTY,Square.O,	 Square.EMPTY},
					          {Square.X,	Square.X,	 Square.O}};
	}
	
	public static Square[][] makeBoard10(){
		return new Square[][]{{Square.X,	Square.EMPTY,Square.O},
							  {Square.X,	Square.EMPTY,Square.X},
					          {Square.O,	Square.EMPTY,Square.EMPTY}};
	}*/
	//6 MOVES
	public static Square[][] makeBoard11(){
		return new Square[][]{{Square.EMPTY,Square.EMPTY,Square.O},
							  {Square.EMPTY,Square.X,	 Square.X},
					          {Square.O,	Square.X,	 Square.O}};
	}
	
	public static Square[][] makeBoard12(){
		return new Square[][]{{Square.EMPTY,Square.X,	 Square.EMPTY},
							  {Square.O,	Square.EMPTY,Square.O},
					          {Square.X,	Square.O,	 Square.X}};
	}
	/*//7 MOVES
	public static Square[][] makeBoard13(){
		return new Square[][]{{Square.X,	Square.O,	 Square.X},
							  {Square.O,	Square.EMPTY,Square.EMPTY},
					          {Square.X,	Square.O,	 Square.X}};
	}
	
	public static Square[][] makeBoard14(){
		return new Square[][]{{Square.O,	Square.X,	 Square.X},
							  {Square.X,	Square.X,	 Square.O},
					          {Square.O,	Square.EMPTY,Square.EMPTY}};
	}*/
}
