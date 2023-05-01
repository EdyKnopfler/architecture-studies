import StepNavigator from "../stepNavigator/StepNavigator";

export default function Flight({ destinationData, step }) {

  return (
    <>
      <StepNavigator step={step} />
      <h1>
        Voo para {destinationData.name}
        {' - '}
        {step === 'going' ? 'IDA' : 'VOLTA'}
      </h1>
    </>
  )
}