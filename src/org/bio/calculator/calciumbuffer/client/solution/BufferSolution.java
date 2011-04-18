package org.bio.calculator.calciumbuffer.client.solution;

public class BufferSolution extends Solution {

        private double _pH;
        private double _H;
        private double _temperature; // in Celsius
        private List<BufferSolute> _bufferSoluteList = new List<BufferSolute>();
        private List<LigandSolute> _ligandSoluteList = new List<LigandSolute>();
        private List<MetalSolute> _metalSoluteList = new List<MetalSolute>();
        private List<IonSolute> _anionSoluteList = new List<IonSolute>();
        private double _ISC;

        public BufferSolution(double pH, double ISC, double temperature)
        {
            _pH = pH;
            _temperature = temperature;
            _ISC = ISC;
            _H = CalculateH(pH, temperature, ISC);
        }

        public double CalculateH(double pH, double temperature, double ionicStrength)
        {
            return Math.Pow(10,-pH)/ gammaH(temperature, ionicStrength);
        }

        private double gammaH(double temperature, double ionicStrength) // per Bers et al. eqn. 15:
        {
            double B = 0.522932 * Math.Exp(0.0327016 * temperature) + 4.015942;
            return 0.145045 * Math.Exp(-B * ionicStrength) + 0.063546 * Math.Exp(-43.97704 * ionicStrength) + 0.695634;
        }

        public void Add(BufferSolute bufferSolute)
        {
            _bufferSoluteList.Add(bufferSolute);
        }

        public void Add(LigandSolute ligandSolute)
        {
            _ligandSoluteList.Add(ligandSolute);
        }

        public void Add(MetalSolute metalSolute)
        {
            _metalSoluteList.Add(metalSolute);
        }

        public void Add(IonSolute anionSolute)
        {
            _anionSoluteList.Add(anionSolute);
        }

        public void UpdateConcentrations(state state)
        {
            //TODO -- MAIN ITERATED LOOP
            /*
             * last values = 0;
             * foreach ligand in list, for each metal in list, update free & total
             * check if converging or diverging; if diverging, error?
             * check if converged; if so, done, report values
             * last values = these values, goto 10
             */
            //////////////////////////////////////////////////////////////////////////////////////////////////
        }

        public double ISC(BufferSolution bufferSolution)  // might be used to find difference between sum-of-parts ISC and desired
        {
            double sumISC = 0;

            foreach (BufferSolute bufferSolute in bufferSolution.bufferSoluteList)
            {
                sumISC += bufferSolute.ISC;
            }
            foreach (LigandSolute ligandSolute in bufferSolution.ligandSoluteList)
            {
                sumISC += ligandSolute.ISC;
            }
            foreach (MetalSolute metalSolute in bufferSolution.metalSoluteList)
            {
                sumISC += metalSolute.ISC;
            }
            foreach (IonSolute anionSolute in bufferSolution.anionSoluteList)
            {
                sumISC += anionSolute.ISC;
            }

            return sumISC;
        }

        public double pH
        {
            get { return _pH; }
            set { _pH = value; }
        }

        public double H
        {
            get { return _H; }
            set { _H = value; }
        }

        public List<BufferSolute> bufferSoluteList
        {
            get { return _bufferSoluteList; }
            //set { _bufferSoluteList = value; }
        }

        public List<LigandSolute> ligandSoluteList
        {
            get { return _ligandSoluteList; }
            //set { _ligandSoluteList = value; }
        }

        public List<MetalSolute> metalSoluteList
        {
            get { return _metalSoluteList; }
            //set { _metalSoluteList = value; }
        }

        public List<IonSolute> anionSoluteList
        {
            get { return _anionSoluteList; }
            //set { _anionSoluteList = value; }
        }
   
	
}
