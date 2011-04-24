package org.bio.calculator.calciumbuffer.client.solution;

import java.util.ArrayList;
import java.util.List;

import org.bio.calculator.calciumbuffer.client.solute.IonSolute.State;
import org.bio.calculator.calciumbuffer.client.solute.BufferSolute;
import org.bio.calculator.calciumbuffer.client.solute.IonSolute;
import org.bio.calculator.calciumbuffer.client.solute.LigandSolute;
import org.bio.calculator.calciumbuffer.client.solute.MetalSolute;

public class BufferSolution extends Solution {

        private double pH;
        private double H;
        private double temperature; // in Celsius
        
        private List<BufferSolute> bufferSoluteList = new ArrayList<BufferSolute>();
        private List<LigandSolute> ligandSoluteList = new ArrayList<LigandSolute>();
        private List<MetalSolute> metalSoluteList = new ArrayList<MetalSolute>();
        private List<IonSolute> anionSoluteList = new ArrayList<IonSolute>();
        private double ISC;

        public BufferSolution(double newpH, double newISC, double newTemperature)
        {
            pH = newpH;
            temperature = newTemperature;
            ISC = newISC;
            H = CalculateH(pH, temperature, ISC);
        }

        public double CalculateH(double pH, double temperature, double ionicStrength)
        {
            return Math.pow(10,-pH)/ gammaH(temperature, ionicStrength);
        }

        private double gammaH(double temperature, double ionicStrength) // per Bers et al. eqn. 15:
        {
            double B = 0.522932 * Math.exp(0.0327016 * temperature) + 4.015942;
            return 0.145045 * Math.exp(-B * ionicStrength) + 0.063546 * Math.exp(-43.97704 * ionicStrength) + 0.695634;
        }

        public void Add(BufferSolute bufferSolute)
        {
            this.bufferSoluteList.add(bufferSolute);
        }

        public void Add(LigandSolute ligandSolute)
        {
            ligandSoluteList.add(ligandSolute);
        }

        public void Add(MetalSolute metalSolute)
        {
            metalSoluteList.add(metalSolute);
        }

        public void Add(IonSolute anionSolute)
        {
            anionSoluteList.add(anionSolute);
        }

        public void UpdateConcentrations(State state)
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

            for (BufferSolute bufferSolute : bufferSolution.bufferSoluteList)
            {
                sumISC += bufferSolute.getISC();
            }
            for (LigandSolute ligandSolute : bufferSolution.ligandSoluteList)
            {
                sumISC += ligandSolute.getISC();
            }
            for (MetalSolute metalSolute : bufferSolution.metalSoluteList)
            {
                sumISC += metalSolute.getISC();
            }
            for (IonSolute anionSolute : bufferSolution.anionSoluteList)
            {
                sumISC += anionSolute.getISC();
            }

            return sumISC;
        }

        public double getpH ()
        {
            return pH; 
        }
        
        public void setpH (double newpH)
        {
            pH = newpH;
        }
        
        public double getH ()
        {
            return H; 
        }

        public void setH (double newH)
        {
            H = newH;
        }
        
        public List<BufferSolute> getBufferSoluteList ()
        {
            return bufferSoluteList; 
            //set { _bufferSoluteList = value; }
        }

        public List<LigandSolute> getLigandSoluteList ()
        {
            return ligandSoluteList; 
            //set { _ligandSoluteList = value; }
        }

        public List<MetalSolute> getMetalSoluteList()
        {
            return metalSoluteList; 
            //set { _metalSoluteList = value; }
        }

        public List<IonSolute> getAnionSoluteList()
        {
            return anionSoluteList; 
            //set { _anionSoluteList = value; }
        }
}
